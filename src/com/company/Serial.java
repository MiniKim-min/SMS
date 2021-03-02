package com.company;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.company.SerialReader;
import com.company.SerialWriter;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Serial {
    static BufferedInputStream bufferedInputStream;
    String feedback = "";
    String data = "";
    public Serial() {
        super();
    }

    void connect(String portName) throws Exception {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);

                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();
                bufferedInputStream = new BufferedInputStream(in);

                (new Thread(new SerialReader(in))).start();
                (new Thread(new SerialWriter(out))).start();

                ArrayList<String> warn = new ArrayList<String>();
                warn.add("first Warning");
                warn.add("second Warning");
                warn.add("third Warning");

                String[][] users = {{"Kim", "01080702270",warn.get(0).toString()},
                        {"Park","01022222222",warn.get(1).toString()},
                        {"Lee","01033333333",warn.get(2).toString()}};
                if(warn.get(0).toString()=="first Warning") {

                }
                try
                {
                    if(!(out == null))
                    {
                        String CSCS = "AT+CSCS=\"IRA\"\r\n"; //Soft reset
                        out.write(CSCS.getBytes());
                        Thread.sleep(500);
                        String CMGF = "AT+CMGF=1\r\n";
                        out.write(CMGF.getBytes());
                        Thread.sleep(500);
                        String CSMP= "AT+CSMP=,,,0\r\n";
                        out.write(CSMP.getBytes());
                        Thread.sleep(500);

                        String CMGS= "AT+CMGS=\""+users[0][1]+"\"\r\n";
                        out.write(CMGS.getBytes());
                        Thread.sleep(500);
                        //String z= "\\x1A";
                        String text="abcdefg";
                        out.write(text.getBytes());
                        Thread.sleep(500);
                        char z = 26;
                        out.write(z);
                        out.flush();
                    }
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }

        }
    }
}