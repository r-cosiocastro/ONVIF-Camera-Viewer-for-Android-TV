package com.rafaelcosio.securitycameraviewer.data.scanner

import android.util.Log
import com.rafaelcosio.securitycameraviewer.domain.model.OnvifDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.UUID
import java.util.regex.Pattern



class CameraScanner {

    companion object {
        private const val TAG = "OnvifScanner"
        private const val WS_DISCOVERY_PORT = 3702
        private const val WS_DISCOVERY_ADDRESS = "239.255.255.250"
        private const val WS_DISCOVERY_PROBE_MESSAGE =
            "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\" xmlns:tns=\"http://schemas.xmlsoap.org/ws/2005/04/discovery\">" +
                    "<soap:Header>" +
                    "<wsa:Action soap:mustUnderstand=\"1\">http://schemas.xmlsoap.org/ws/2005/04/discovery/Probe</wsa:Action>" +
                    "<wsa:MessageID>urn:uuid:%s</wsa:MessageID>" + // UUID dinámico
                    "<wsa:ReplyTo>" +
                    "<wsa:Address>http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous</wsa:Address>" +
                    "</wsa:ReplyTo>" +
                    "<wsa:To soap:mustUnderstand=\"1\">urn:schemas-xmlsoap-org:ws:2005:04:discovery</wsa:To>" +
                    "</soap:Header>" +
                    "<soap:Body>" +
                    "<tns:Probe>" +
                    "</tns:Probe>" +
                    "</soap:Body>" +
                    "</soap:Envelope>"
        private const val SCAN_TIMEOUT_MS = 5000
    }

    suspend fun scanForOnvifDevices(): List<OnvifDevice> = withContext(Dispatchers.IO) {
        val discoveredDevices = mutableListOf<OnvifDevice>()
        var socket: DatagramSocket? = null

        try {
            socket = DatagramSocket()
            socket.broadcast = true

            val messageId = UUID.randomUUID().toString()
            val probeMessage = String.format(WS_DISCOVERY_PROBE_MESSAGE, messageId)
            val sendData = probeMessage.toByteArray()

            val sendPacket = DatagramPacket(
                sendData,
                sendData.size,
                InetAddress.getByName(WS_DISCOVERY_ADDRESS),
                WS_DISCOVERY_PORT
            )

            Log.d(TAG, "Enviando Probe ONVIF...")
            socket.send(sendPacket)

            val startTime = System.currentTimeMillis()
            val buffer = ByteArray(4096)

            while (System.currentTimeMillis() - startTime < SCAN_TIMEOUT_MS) {
                val receivePacket = DatagramPacket(buffer, buffer.size)
                socket.soTimeout = SCAN_TIMEOUT_MS - (System.currentTimeMillis() - startTime).toInt()

                try {
                    socket.receive(receivePacket)
                    val response = String(receivePacket.data, 0, receivePacket.length)
                    Log.d(TAG, "Respuesta recibida de ${receivePacket.address.hostAddress}:\n$response")


                    // TODO: implementar XmlPullParser
                    val xAddrs = extractValues(response, "XAddrs")
                    val scopes = extractValues(response, "Scopes")

                    if (xAddrs.isNotEmpty()) {
                        val deviceAddress = receivePacket.address.hostAddress ?: "unknown"
                        // Evitar duplicados basados en la dirección IP o un identificador único del XAddr
                        if (discoveredDevices.none { it.address == deviceAddress || it.xAddrs.any { xaddr -> xAddrs.contains(xaddr) }}) {
                            Log.i(TAG, "Dispositivo ONVIF encontrado: $deviceAddress, XAddrs: $xAddrs")
                            discoveredDevices.add(OnvifDevice(xAddrs, scopes, deviceAddress))
                        }
                    }
                } catch (e: java.net.SocketTimeoutException) {
                    Log.d(TAG, "Timeout esperando respuesta.")
                    break
                } catch (e: Exception) {
                    Log.e(TAG, "Error recibiendo/procesando paquete: ${e.message}", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error durante el escaneo ONVIF: ${e.message}", e)
        } finally {
            socket?.close()
        }
        Log.d(TAG, "Escaneo finalizado. Encontrados ${discoveredDevices.size} dispositivos.")
        return@withContext discoveredDevices.distinctBy { it.address }
    }

    private fun extractValues(xmlResponse: String, tagName: String): List<String> {
        val values = mutableListOf<String>()
        // Regex simple, puede no ser robusta para todos los casos de XML
        val pattern = Pattern.compile("<[^:]*:$tagName>([^<]+)</[^:]*:$tagName>", Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(xmlResponse)
        while (matcher.find()) {
            matcher.group(1)?.let { values.addAll(it.split("\\s+".toRegex())) }
        }
        return values.mapNotNull { it.trim().takeIf { s -> s.isNotEmpty() } }
    }
}