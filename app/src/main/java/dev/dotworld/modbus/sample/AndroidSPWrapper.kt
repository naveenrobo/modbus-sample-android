package dev.dotworld.modbus.sample

import com.telpo.tps550.api.serial.Serial
import com.zgkxzx.modbus4And.serial.SerialPortWrapper
import java.io.InputStream
import java.io.OutputStream


public class AndroidSPWrapper(private val port: String) : SerialPortWrapper {

    companion object {
        private lateinit var serial: Serial
    }

    fun getSerial(): Serial {
        return serial
    }

    override fun close() {
        serial.close()
    }

    override fun open() {
        serial = Serial(port, baudRate, 0)
    }

    override fun getInputStream(): InputStream {
        return serial.inputStream
    }

    override fun getOutputStream(): OutputStream {
        return serial.outputStream
    }

    override fun getBaudRate(): Int {
        return 115200
    }

    override fun getFlowControlIn(): Int {
        return 0
    }

    override fun getFlowControlOut(): Int {
        return 0
    }

    override fun getDataBits(): Int {
        return 8
    }

    override fun getStopBits(): Int {
        return 1
    }

    override fun getParity(): Int {
        return 0
    }
}