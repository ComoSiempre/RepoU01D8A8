package com.civi.jni;

/**
 * The peripherals API.
 * @author wuxili@cv-it.com
 *
 */
public class PeripheralsJNI {
	static {
		System.loadLibrary("peripheral");
	}
	//GPIO
    /**
     * Open the device file(Control GPIO).
     * @return The device file descriptor
     */
    public native static int openGPIODev();
    
    /**
     * Control the GPIO.
     * @param fd The device file descriptor.
     * @param gpioNum The gpio number.
     * gpioNum = 1, control GPO 1.
     * gpioNum = 2, control GPO 2.
     * gpioNum = 3, control GPO 3.
     * gpioNum = 4, control GPO 4.
     * @param value Set the gpio state value.
     * value = 1, pull up.
     * value = 0, pull down.
     * @return 0 - Successful.
     */
    public native static int GPIOControl(int fd, int gpioNum, int value);
  
    /**
     * Get GPIO state.
     * @param fd The device file descriptor.
     * @param buff Get the gpio state value.
     * @param gpioChannel Set the operate gpio number.
     * gpioChannel = 1, read GPI 1.
     * gpioChannel = 2, read GPI 2.
     * gpioChannel = 3, read GPI 3.
     * gpioChannel = 4, read GPI 4.
     * gpioChannel = 5, read GPO 1.
     * gpioChannel = 6, read GPO 2.
     * gpioChannel = 7, read GPO 3.
     * gpioChannel = 8, read GPO 4.
     * @return 0 - Successful.
     */
    public native static int getGPIOStatus(int fd, byte[] buff, int gpioChannel);
    
    /**
     * Close the device file(Control GPIO).
     * @param fd The device file descriptor.
     * @return 0 - Successful.
     */
    public native static int closeGPIODev(int fd);

    /**
     * Open the device file(Control 3G/Wifi modules).
     * @return The device file descriptor.
     */
    public native static int openGWDev();
    
    /**
     * Control the 3G/Wifi modules.
     * @param fd The device file descriptor.
     * @param gwNum Set the operate module number.
     * gwNum = 1, control 3G module.
     * gwNum = 2, control Wifi module.
     * @param value Set the module state value.
     * value = 1, open module.
     * value = 0, close module.
     * @return 0 - Successful.
     */
    public native static int gwControl(int fd, int gwNum, int value);
    
    /**
     * Close the device file(Control 3G/Wifi modules).
     * @param fd The device file descriptor.
     * @return 0 - Successful.
     */
    public native static int closeGWDev(int fd);

    //Relay
    /**
     * Open the device file(Control relay).
     * @return The device file descriptor.
     */
    public native static int openRelayDev();
     
    /**
     * Control the relay.
     * @param fd The device file descriptor.
     * @param relayNum Set the operate relay number.
     * relayNum = 1, control relay1.
     * relayNum = 2, control relay2.
     * @param value Set the relay state value.
     * value = 1, pull up.
     * value = 0, pull down.
     * @return 0 - Successful.
     */
    public native static int relayControl(int fd, int relayNum, int value);
    
    /**
     * Get the relay state.
     * @param fd The device file descriptor.
     * @param buff Get the relay state value.
     * @param relayNum Set the operate relay number.
     * @return 0 - Successful.
     */
    public native static int getRelayStatus(int fd, byte[] buff, int relayNum);
    
    /**
     * Close the device file(Control relay).
     * @param fd The device file descriptor.
     * @return 0 - Successful.
     */
    public native static int closeRelayDev(int fd);

    // IR sensor
    /**
     * Open the device file(Control IR sensor).
     * @return The device file descriptor.
     */
    public native static int openIRSensorDev();
    
    /**
     * Read IR sensor state.
     * @param fd The device file descriptor.
     * @return Get the IR sensor state value.
     * 0 - close.
     * 1 - open.
     */
    //public native static int irReadState(int fd);
    public native static int irReadState(int fd,  byte[] retData);
    /**
     * Control the IR sensor.
     * @param fd The device file descriptor.
     * @param value Set the IR Sensor state value.
     * @return 0 - Successful.
     */
    public native static int irSensorControl(int fd, int value);
    
    
    /**
     * Close the device file(Control IR sensor).
     * @param fd The device file descriptor.
     * @return 0 - Successful.
     */
    public native static int closeIRSensorDev(int fd);
    
	// Tamper Proof
	/**
	 * Open the device file(Tamper proof).
	 * @return The device file descriptor.
	 */
	public native static int openTamperProof();
	
    /**
     * Get the tamper proof state.
     * @param fd The device file descriptor.
     * @return The device state value.
     * 1 - Press.
     * 0 - Normal.
     */
    public native static int checkTamperProofSensor(int fd);
    
    /**
     * Close the device file(Tamper proof).
     * @param fd The device file descriptor.
     * @return 0 - Successful.
     */
    public native static int closeTamperProof(int fd);

    // Wiegand output
    /**
     * Open the device file(Wiegand output).
     * @return The device file descriptor.
     * @param  index = 0 Wiegand output device. 
     */
    public native static int openWiegandDev(int index);
    
    /**
     * Close the device file(Wiegand output).
     * @param fd The device file descriptor.
     * @return 0 - Successful.
     */
    public native static int closeWiegandDev(int fd);
    
    /**
     * Send data by wiegand output.
     * @param fd The device file descriptor.
     * @param data Send data.
     * @param dataLen Send data length.
     * @return 0 - Successful.
     */
    public native static int sendData(int fd, byte[] data, int dataLen);
    
    //Wiegand input
    /**
     * Open the device file(Wiegand input). 
     * @return The device file descriptor(Wiegand input device).
     */
    public native static int openWiegand();
    
    /**
     * Close the device file(Wiegand input).
     * @param fd The device file descriptor.
     * @return 0 - Successful.
     */
    public native static int closeWiegand(int fd);
    
    /**
     * Receive data from wiegand input. 
     * @param fd The device file descriptor.
     * @param buff Receive data.
     * @param buffLen Receive data length.
     *  Wiegand 26 - 3 bytes
	 *	Wiegand 32 - 4 bytes
	 *	Wiegand 32e - 4 bytes
	 *	Wiegand 34 - 4 bytes
	 *	Wiegand 40 - 5 bytes
	 * 	Wiegand 42 - 5 bytes
	 *	Wiegand 50 - 6 bytes
	 *	Wiegand 56 - 7 bytes
	 *	Wiegand 58 - 7 bytes
     * @return Receive data length.
     */
	public native static int readWiegandData(int fd, byte[] buff, int buffLen);
	
    // Communication Channel
    /**
     * Open the device file(Communication channel).
     * @return The device file descriptor.
     */
    public native static int openCommChDev();
    
    /**
     * Control the communication channel.
     * @param fd The device descriptor.
     * @param channel Set the  communication channel value.
     * channel = 2, RS232.
     * channel = 3, RS485.
     * channel = 4, MCU.
     * @return 0 - Successful.
     */
    public native static int commChControl(int fd, int channel);
    
    /**
     * Close the device file(Communication channel).
     * @param fd The device file descriptor.
     * @return 0 - Successful.
     */
    public native static int closeCommChDev(int fd);
    
    // RS485 control
    /**
     * Open the device file(Enable RX/TX).
     * @return The device file descriptor.
     */
    public native static int openRS485();
    
    /**
     * Enable the RX/TX.
     * @param fd The device file descriptor.
     * @param value The RS485 state value.
     * value = 1, RS485 RX_ENABLE.
     * value = 2, RS485 TX_ENABLE.
     * @return 0 - Successful.
     */
    public native static int RS485Control(int fd, int value);
    
    /**
     * Close the device file(Enable RX/TX).
     * @param fd The device file descriptor.
     * @return 0 - Successful.
     */
    public native static int closeRS485(int fd);
    
    // RFID&Finger Module Control
    /**
     * Open the device file(Control Reader&Finger module).
     * @return The device file descriptor.
     */
    public native static int openRFDev();
    
    /**
     * Get the module state.
     * @param fd The device file descriptor.
     * @param buff Get the R/F state value.
     * @param rfChannel Set the operate peripheral number.
     * rfChannel = 1, read RFID.
     * rfChannel = 2, read Finger.
     * @return 0 - Successful.
     */
    public native static int rfReadState(int fd, byte[] buff, int rfChannel);
    
    /**
     * Open/Close the module.
     * @param fd The device file descriptor.
     * @param rfNum Set the operate module number.
     * rfNum = 1, control RFID.
     * rfNum = 2, control Finger.
     * @param value Set the module state value.
     * value = 1, open device.
     * value = 0, close device.
     * @return 0 - Successful.
     */
    public native static int rfControl(int fd, int rfNum, int value);
    
    /**
     * Close the device file(Control Reader&Finger module).
     * @param fd The device file descriptor.
     * @return 0 - Successful.
     */
    public native static int closeRFDev(int fd);
    
    /**
     * Open the device file(Set IR distance).
     * @return The device file descriptor.
     */
	public native static int openPWMDev();
	
	/**
	 * Set IR sensor distance.
	 * @param fd The device file descriptor.
	 * @param value PWM distance value.
	 * @return 0 - Successful.
	 */
	public native static int setIRPWMDistance(int fd, int value);
	
	/**
	 * Close the device file(Set IR distance).
	 * @param fd The device file descriptor.
	 * @return 0 - Successful.
	 */
	public native static int closePWMDev(int fd);
	
	/**
	 * Open the device file(RS232/RS485 communication).
	 * @return The device file descriptor.
	 */
	public native static int openTTYSACDev();
	
	/**
	 * Send data to RS232/RS485.
	 * @param buff
	 * @return 0 - Successful.
	 */
	public native static int writeDataToRS(byte[] buff);
	
	/**
	 * Close the device file(RS232/RS485 communication).
	 * @return 0 - Successful.
	 */
	public native static int closeTTYSACDev();
	
	//Reader control
	/**
	 * Open the device file(Control Reader GPIO).
	 * @return The device file descriptor.
	 */
    public native static int openReadercontrol();
    /**
     * Control the reader GPIO.
     * @param fd The device file descriptor.
     * @param ReadercontrlNum Set the operate relay number.
     * controlNum = 1, control Reader Reset pin.
     * controlNum = 2, control Reader Boot pin.
     * controlNum = 3, control Reader IAP pin.
     * @param value Set the pin state value.
     * value = 1, pull up.
     * value = 0, pull down.
     * @return 0 - Successful.
     */
    public native static int readerControl(int fd, int controlNum, int value);
    
    /**
     * Close the device file(Control Reader GPIO).
     * @param fd The device file descriptor.
     * @return 0 - Successful.
     */
    public native static int closeReadercontrl(int fd);
    
    //MCU control
    
    /**
     * Open the device file(Control MCU GPIO).
     * @return The device file descriptor.
     */
    public native static int openMCUcontrol();
    
    /**
     * Control the MCU GPIO.
     * @param fd The device file descriptor.
     * @param MCUcontrlNum Set the operate relay number.
     * controlNum = 1, control MCU Reset pin.
     * controlNum = 2, control MCU ISP pin(V2.2).
     * controlNum = 3, control MCU Power pin.
     * controlNum = 4, control MCU ISP pin(V2.3).
     * @param value Set the pin state value.
     * value = 1, pull up.
     * value = 0, pull down.
     * @return 0 - Successful.
     */
    public native static int MCUControl(int fd, int controlNum, int value);
    
    /**
     * Close the device file(Control MCU GPIO).
     * @param fd The device file descriptor.
     * @return 0 - Successful.
     */
    public native static int closeMCU(int fd);
      
    //Hardware version 
    /**
     * Open the device file(Hardware version).
     * @return The device file descriptor.
     */
    public native static int OpenHVDevice();
    
    /**
     * Get hardware version information.
     * @param fd  The device file descriptor.
     * @param hv Hardware version.
     * @return 0 - Successful.
     */
    public native static int getHardwareVersion(int fd, byte[] hv);
    
    /**
     * Close the device file(Hardware version).
     * @param fd The device file descriptor.
     * @return 0 - Successful.
     */
    public native static int closeHVDevice(int fd);
    
    //Touch panel version
    /**
     * Open the device file(TP version).
     * @return The device file descriptor.
     */
    public native static int OpenTPDevice();
    
	/**
	 * Get the TP version information.
	 * @param fd  The device file descriptor.
	 * @param tpv Touch panel version.
	 * @return 0 - Successful.
	 */
    public native static int getTPVersion(int fd, byte[] tpv);
    
	/**
	 * Close the device file(TP version).
	 * @param fd The device file descriptor.
	 * @return 0 - Successful.
	 */
    public native static int closeTPDevice(int fd);
    
    //MCU firmware version
    /**
     * Open the device file(MCU firmware version).
     * @return The device file descriptor.
     */
    public native static int OpenMCUDevice();
    /**
     * @param fd  The device file descriptor.
     * @param mcuv MCU firmware version.
     * @return 0 - Successful.
     */
    public native static int getMCUVersion(int fd, byte[] mcuv);
    /**
	 * Close the device file(version).
	 * @param fd The device file descriptor.
	 * @return 0 - Successful.
	 */
    public native static int closeMCUDevice(int fd); 
    
    /**
     * get the Network Interface Card MAC address.
     * @param MACData MAC address.
     * @return 0 - Successful.
     */
    public native static int getMACAddress(byte[] MACData);     
}
