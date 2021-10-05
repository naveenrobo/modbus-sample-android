/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2006-2011 Serotonin Software Technologies Inc. http://serotoninsoftware.com
 * @author Matthew Lohbihler
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.zgkxzx.modbus4And.msg;

import com.zgkxzx.modbus4And.ProcessImage;
import com.zgkxzx.modbus4And.code.FunctionCode;
import com.zgkxzx.modbus4And.exception.ModbusTransportException;

public class ReadDiscreteInputsRequest extends ReadBinaryRequest {
    public ReadDiscreteInputsRequest(int slaveId, int startOffset, int numberOfBits) throws ModbusTransportException {
        super(slaveId, startOffset, numberOfBits);
    }

    ReadDiscreteInputsRequest(int slaveId) throws ModbusTransportException {
        super(slaveId);
    }

    @Override
    public byte getFunctionCode() {
        return FunctionCode.READ_DISCRETE_INPUTS;
    }

    @Override
    ModbusResponse handleImpl(ProcessImage processImage) throws ModbusTransportException {
        return new ReadDiscreteInputsResponse(slaveId, getData(processImage));
    }

    @Override
    protected boolean getBinary(ProcessImage processImage, int index) throws ModbusTransportException {
        return processImage.getInput(index);
    }

    @Override
    ModbusResponse getResponseInstance(int slaveId) throws ModbusTransportException {
        return new ReadDiscreteInputsResponse(slaveId);
    }
}
