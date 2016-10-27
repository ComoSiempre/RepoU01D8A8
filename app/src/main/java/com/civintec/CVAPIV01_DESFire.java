package com.civintec;

/**
 * The RFID Reader API.
	Note:	
	Can't add pictures and tables in the javadoc.
	Details parameters please refer to the CVAPIV01_DESFire.java,
	and also you can refer to the CNReader API Reference(CNReader API Reference V3.61.pdf).
 * @author wuxili@cv-it.com
 *
 */
public class CVAPIV01_DESFire{
	static{
		System.loadLibrary("CVAPIV01_DESFire");
	}
	
	/**
	 * Set the communication type between computer and reader,
	 * before you call any other API, 
	 * you must select a type to communicate with reader (call this API first). 
	 * And if your reader interface is USB, you need installing USB driver first.
	 * @param bType Communication type between computer and reader. 
	 * There 3 type interface
	 * 0-RS232 
	 * 1-USB 
	 * 2-UDP 
	 * @return 0 - Successful.
	 */
	public native static int CV_SetCommunicationType(byte bType);
	
	public native static int Set_USBNum(int Communication_Type);
	
	/**
	 * Get the communication type between computer and reader.
	 * @return The communication type between computer and reader.
	 * 0-RS232.
	 * 1-USB.
	 * 2-UDP.
	 */
	public native static int CV_GetCommunicationType();
	
	/**
	 * Get the Version Nmber of the API
	 * @param bVersion Character pointer to C string which return the Version Number of the API.
	 * @return 0 - Successful.
	 */
	public native static int GetVersionAPI(byte[] bVersion);
	
	/**
	 * Open the comm port and set the baud rate for further communication with the reader.
	 * @param bCom Character pointer to C string of the name of the serial port where the reader is connected. (e.g. COM1, COM2, COM3, COM4)
	 * @param nBaudrate The communication baud rate of serial port(Possible values : 9600, 19200, 38400, 57600, 115200).
	 * @param nType 0 - /dev/ttySAC.
	 * 1 - /dev/ttyUSB/.
	 * 2 - dev/ttyM.
	 * @return 0 - Successful.
	 */
	public native static int OpenComm(byte[] bCom, int nBaudrate, int nType);
	
	/**
	 * Close the communication port. The CloseComm( ) should be called to release the serial port before closing the application program.
	 * @return 0 - Successful.
	 */
	public native static int CloseComm();
	
	/** 
	 * The SetDeviceAddress() function programs a device address to the reader. If the Enable Serial Number Checking Flag is set, the correct Reader Serial Number must be submitted to program the device address.
	 * @param nDevAddr Device Address of the reader.
	 * @param bNewAddr The new device address to be programmed to the reader.
	 * @param bMode 0x00 - Disable Checking of the Serial Number.
	 * 0x01 - Enable Checking of the Serial Number.
	 * @param bBuffer Pointer to the string storing the Serial Number.
	 * @return 0 - Successful.
	 */
	public native static int SetDeviceAddress(int nDevAddr, byte[] bNewAddr, byte bMode, byte[] bBuffer);
	
	/**
	 * Get the Device Address and the Serial Number from the reader.
	 * @param nDevAddr Device Address of the reader.
	 * @param nCurrentAddr Device address returned.
	 * @param bSerialNum Pointer to the string storing the Serial Number returned.
	 * @return 0 - Successful.
	 */
	public native static int GetSerialNum(int nDevAddr, int[] nCurrentAddr, byte[] bSerialNum);
	
	/**
	 * Set the Serial Number for the reader.
	 * @param nDevAddr Device Address of the reader.
	 * @param bSerialNum Pointer to the 4 bytes serial number which will be set for the reader.
	 * @return 0 - Successful.
	 */
	public native static int SetSerialNum(int nDevAddr, byte[] bSerialNum);
	
	/**
	 * Get the Reader's Firmware version number.
	 * @param nDevAddr Device Address of the reader.
	 * @param bVerNum Pointer to the string of the Version Number. The version number is the firmware version of the reader device.
	 * @return 0 - Successful.
	 */
	public native static int GetVersionNum(int nDevAddr, byte[] bVerNum);
	
	/**
	 * Change the communication baud rate of the reader.
	 * Note: the SetRDRBaudrate() modify the  default baud  rate stored in the reader's internal EEPROM. The new setting will not take effect until the next reset of the reader.
	 * Note: Need to change the baud rate of the Host controller correspondly. 

	 * @param nDevAddr Device Address of the reader.
	 * @param bBaudrate The baudrate of the Reader Module.
	 * 0x01 - 9600bps.
	 * 0x02 - 19200bps.
	 * 0x03 - 38400bps.
	 * 0x04 - 57600bps.
	 * 0x05 - 115200bps.
	 * @return 0 - Successful.
	 */
	public native static int SetFirmwareBaudrate(int nDevAddr, byte bBaudrate);
	
	/**
	 * The LED is blinking for the number of cycles. Each cycle is one second. The turn-on time of the LED in each cycle is set by the 'on-time'. 
	 * @param nDevAddr Device Address of the reader.
	 * @param bNumLED The LED to be selected.
	 * 0x01 - Red LED.
	 * 0x02- Green LED.
	 * 0x03 - Both Red & Green LED.
	 * @param bTime Units of the LED turn-on time (duty cycle). Each unit is 100ms.
	 * @param bCycle Number of cycles that the LED will be turned on and off.
	 * @return 0 - Successful.
	 */
	public native static int ActiveLED(int nDevAddr, byte bNumLED, byte bTime, byte bCycle);
	
	/**
	 * Turn on/off the selected LEDs.
	 * @param nDevAddr Device Address of the reader.
	 * @param bNumLED Turn on/off the LEDs.
	 * Bit0 - Red LED. 0= LED on, 1= LED off.
	 * Bit1 - Green LED. 0= LED on, 1= LED off.
	 * Bit2-7 - unused
	 * @return 0 - Successful.
	 */
	public native static int SetLED(int nDevAddr, byte bNumLED);
	
	/**
	 * Control the buzzer. You can control the buzzer to play a sound pattern by using mode 4.
	 * @param nDevAddr Device Address of the reader.
	 * @param bMode Buzzer mode.
	 * 0 - Turn off the buzzer.
	 * 1 - Turn on the buzzer.
	 * 4 - Play a sound pattern. The pattern is a sequence of on-off-on-off sound controlled by the parameter array 'pattern'
	 * @param bBuffer Pointer to a parameter array which controls the sound pattern.
	 * pattern[0]:	Units of first on time. Each unit is 100ms.
	 * pattern[1]:	Units of first off time.
	 * pattern[2]:	Units of second on time.
	 * pattern[3]:	Units of second off time.
	 * pattern[4]:	Cycle.
	 * @return 0 - Successful.
	 */
	public native static int ActiveBuzzer(int nDevAddr, byte bMode, byte[] bBuffer);
	
	/** 
	 * ISO14443A general command(Time out set to 0x00). Used to Acces ISO14443A CPU Card , e.g. Pro(X), DesFire.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CRC_Flag Enable Flag.
	 * 0x00 - No CRC checksum will be calculated and appended.
	 * 0x01 - The CRC checksum will be calculated and appended.
	 * @param length The byte number of buffer.
	 * @param buffer APDU to/form card.
	 * @return 0 - Successful.
	 */
	public native static int SLE_Generic(int DeviceAddress,byte CRC_Flag,byte[] length,byte[] buffer);
	
	/**
	 * ISO14443A general command. Used to Acces ISO14443A CPU Card , e.g. Pro(X), DesFire.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CRC_Flag Enable Flag.
	 * 0x00 - No CRC checksum will be calculated and appended.
	 * 0x01 - The CRC checksum will be calculated and appended.
	 * @param TimeOut Reader will execute the command within a time (20 * TimeOut  ms), if the time expire,   reader will return back regardless of whether command finish or not.
	 * @param length The byte number of buffer.
	 * @param buffer APDU to/form card.
	 * @return 0 - Successful.
	 */
	public native static int SLE_GenericEx(int DeviceAddress,byte CRC_Flag,byte TimeOut,byte[] length,byte[] buffer);  
	
	/**
	 * Read multiple (up to four) blocks from the Mifare card, the latest version has changed the implementation for this API, it will return num_blk*16 bytes data once which is different from old version(one time return 16 bytes, total times is num_blk).
	 * Note: The blocks to be read must be in the same sector.
	 * @param DeviceAddress Device Address of the reader.
	 * @param add_blk Start address of memory blocks to read.
	 * @param num_blk Number(1 -4 ) of block to read.
	 * @param buffer Pointer to the buffer which returns the data read from the card. The length of the buffer equals to num_blk *16 bytes.
	 * @return 0 - Successful.
	 */
	public native static int MF_Read(int DeviceAddress,byte add_blk, byte num_blk, byte[] buffer);
	
	/**
	 * This command is used to authenticate the selected Mifare card. Further read/write and value related operations are allowed only after the successful authentication.
	 * @param DeviceAddress Device Address of the reader.
	 * @param mode Key A or B selection.
	 * 0x60 : Use KEYA for authentication.
	 * 0x61: Use KEYB for authentication.
	 * @param snr Pointer to a four-bytes buffer which stores the UID(card serial number) of the card to be authenticated.
	 * @param block The address of the block (block number : 00..63) to be authenticated.
	 * @return 0 - Successful.
	 */
	public native static int MF_Auth(int DeviceAddress,byte mode,byte[] snr, byte block);
	
	/**
	 * Write multiple (up to four) blocks data to the Mifare card. 
	Note : The blocks to be written must be in the same sector. Writing data to the sector trailer (block address =  N*4+3, where N is the sector number) should be handled carefully, otherwise you may corrupt the KEY area and lock the sector.
	 * @param DeviceAddress Device Address of the reader.
	 * @param add_blk Start address of memory blocks to write.
	 * @param num_blk Number(1 -4 ) of block to write.
	 * @param buffer Pointer to the buffer which stores the data to be written to the card. The length of the buffer equals to num_blk *16 bytes.
	 * @return 0 - Successful.
	 */
	public native static int MF_Write(int DeviceAddress,byte add_blk, byte num_blk, byte[] buffer);
	
	/**
	 * @param DeviceAddress Device Address of the reader.
	 * @param StartPage The page address to be read. Only addresses from 0x00 to 0x0F can be decoded
	 * @param FromData_16bytes Data be read from UltraLight card, Read the 16 bytes data from StartPage address orderly.  (e.g. if StartPage is '0x03' pages 0x03, 0x04, 0x05, 0x06 are returned).
	 * @return 0 - Successful.
	 */
	public native static int MF_UL_Read(int DeviceAddress,int StartPage , byte[] FromData_16bytes);
	
	/**
	 * @param DeviceAddress Device Address of the reader.
	 * @param PageNum Number of page address to be written. Only the addresses from 0x02 to 0x0F can be decoded.
	 * @param ToData_4bytes Data be write in UltraLight card.. This command is performed 4 bytes in the same page-wise.
	 * @return 0 - Successful.
	 */
	public native static int MF_UL_Write(int DeviceAddress,int PageNum , byte[] ToData_4bytes);
	
	/**
	 * Ultralight C card's authentication procedure with 2Key 3DES. Before read Data from or write Data to the Ultralight C card, should Call this function.
	 * @param DeviceAddress Device Address of the reader.
	 * @param Key 16 bytes length, 2 key 3DES encryption.
	 * @return 0 - Successful.
	 */
	public native static int MF_UL_C_Auth(int DeviceAddress,  byte[] Key);
	
	/**
	 * Increase the value of a MIFARE Value Block. The value block must be pre-initialized according to the MIFARE Value Block Format.
	 * Note : The VALUE is in plain format, the user is no need to take care the MIFARE value block format.
	 * Note: The result of the MIFARE's Decrement or Increment operation is stored within the on-chip buffer register. The value in the selected value block will not be updated until the Transfer command is done.
	 * @param DeviceAddress Device Address of the reader.
	 * @param add_blk Block address of the value block.
	 * @param value The value to be increased.
	 * @return 0 - Successful.
	 */
	public native static int MF_Increment(int DeviceAddress,byte add_blk, int value);
	
	/**
	 * Decrease the value of a MIFARE Value Block. The value block must be initialized according to the MIFARE Value Block Format.
	 * Note : The VALUE is in plain format, the user is no need to take care the MIFARE value block format.
	 * @param DeviceAddress Device Address of the reader. 
	 * @param add_blk Block address of the value block.
	 * @param value The value to be decreased.
	 * @return 0 - Successful.
	 */
	public native static int MF_Decrement(int DeviceAddress,byte add_blk, int value);
	
	/**
	 * Transfer a value amount from the Mifare Reader Chip's internal value buffer register to the selected value block.
	 * @param DeviceAddress Device Address of the reader.
	 * @param add_blk Block address of the value block.
	 * @return 0 - Successful
	 */
	public native static int MF_Transfer(int DeviceAddress,byte add_blk);
	
	/**
	 * Restore the content of the selected Value Block to the MIFARE Reader Chip's internal value buffer register. 
	 * @param DeviceAddress Device Address of the reader.
	 * @param add_blk Block address of the value block.
	 * @return 0 - Successful.
	 */
	public native static int MF_Restore(int DeviceAddress,byte add_blk);
	
	/**
	 * Write (initialize) a value to a MIAFARE value block. The block to be written will be automatically formatted with the MIFARE Value Block Format.
	 * @param DeviceAddress Device Address of the reader.
	 * @param add_blk Block address of the value block.
	 * @param value The value to be written.(Initialize the value block)
	 * @return 0 - Successful.
	 */
	public native static int MF_InitValue(int DeviceAddress,byte add_blk, int value);
	
	/**
	 * Read back the value amount of a MIFARE Value Block. 
	 * @param DeviceAddress Device Address of the reader. 
	 * @param add_blk Block address of the value block.
	 * @param value The value read back from the value block.
	 * @return 0 - Successful.
	 */
	public native static int MF_ReadValue(int DeviceAddress,byte add_blk, int[] value);
	
	/**
	 * Directly load the key to the Master Key Buffer. A Master Key must be loaded to the Master Key Buffer (by MF_LoadKey ( ) or MF_LoadKeyFromEeprom( ) ) before executing the Authentication command.
	 * @param DeviceAddress Device Address of the reader.
	 * @param key Pointer  to a 6-bytes buffer storing the key string.
	 * @return 0 - Successful.
	 */
	public native static int MF_LoadKey(int DeviceAddress,byte[] key);
	
	/**
	 * Stores a key to the reader's EEPROM.  The API is necessary before calling 'Mifare High Level Functions' 
	 * @param DeviceAddress Device Address of the reader.
	 * @param Key_type Select KEYA or KEYB.
	 * 0x60: the Key will be stored as KEYA.
	 * 0x61: the Key will be stored as KEYB.
	 * @param Sector The sector number {0x00-0x0F}: where the key to be stored.
	 * @param Key Pointer  to a 6-bytes buffer storing the uncoded key.string. (i.e. A0A1A2A3A4A5 ).
	 * @return 0 - Successful.
	 */
	public native static int MF_StoreKeyToEE(int DeviceAddress,byte Key_type,byte Sector,byte[] Key);
	
	/**
	 * Load key to the Master Key Buffer from the MFRC500 chip's internal EEPROM. This function has the same function as the MF_LoadKey( ), but the key is loaded from the internal EEPROM instead providing the 6-byte un-coded key string. The API is necessary before calling 'Mifare High Level Functions
	 * @param DeviceAddress Device Address of the reader.
	 * @param Key_type Select KEYA or KEYB.
	 * 0x60 : KEYA will be loaded from the EEProm.
	 * 0x61 : KEYB will be loaded from the EEProm.
	 * @param Sector The sector number {0x00-0x0F} of the key to be loaded.
	 * @return 0 - Successful.
	 */
	public native static int MF_LoadKeyFromEE(int DeviceAddress,byte Key_type,byte Sector);
	
	/**
	 * Send the generic transparent command for Type B card. The command packet is send 
	according to the ISO14443-4 and the card's response packet will be returned. The CRC checksum will be calculated and appended automatically.
	 * @param DeviceAddress Device Address of the reader
	 * @param length Length of the data.
	 * Call : the length of the data send to the contactless card. The data length includes the Header.
	 * Return : the length of the response sent back by the card.
	 * @param buffer Pointer to the buffer for the data to be sent/received.
	 * Call : Data to be sent to the contactless card.  The package format according to the ISO14443-4 format. the  CRC checksum will be appended automatically.
	 * Return : The response from contactless card.
	 * @return 0 - Successful.
	 */
	public native static int Generic_B(int DeviceAddress,byte[] length,byte[] buffer);
	
	/**
	 * Send the generic transparent command for Type B card. The command packet is send according to the ISO14443-4 and the card's response packet will be returned. 
	 * The CRC checksum will be calculated and appended automatically
	 * @param DeviceAddress Device Address of the reader.
	 * @param CRC_Flag Enable Flag .
	 * 0x00 - No CRC checksum will be calculated and appended.
	 * 0x01 - The CRC checksum will be calculated and appended.
	 * @param TimeOut Reader will execute the command within a time (20 * TimeOut  ms), if the time expire, reader will return back regardless of whether command finish or not.
	 * @param length Length of the data
	 * Call : the length of the data send to the contactless card. The data length includes the Header 
	 * Return : the length of the response sent back by the card. 
	 * @param buffer Pointer to the buffer for the data to be sent/received.
	 * Call : Data to be sent to the contactless card.  The package format according to the ISO14443-4 format. the  CRC checksum will be appended automatically.
	 * Return : The response from contactless card.
	 * @return 0 - Successful.
	 */
	public native static int Generic_BEx(int DeviceAddress,byte CRC_Flag,byte TimeOut, byte[] length,byte[] buffer);
	
	/**
	 * Read multiple (up to four) blocks from the Mifare card.
	 * Note: The blocks to be read must be in the same sector. Before calling, the 2 APIs must be called firstly:MF_StoreKeyToEE() and LoadKeyFromEE(), the 2 APIs used to provide correct Keys to save into Reader, being used for authentication.
	 * @param DeviceAddress Device Address of the reader.
	 * @param mode Operating Mode.
	 * Bit0 - All - Select Request all or Request IDLE mode (0/1= IDLE/ALL).
	 * Bit1 - SNR - Enable the Card Serial Number comparison features. The further operating will be processed only if the detected card number matches the card serial number sent by the Host.
	 * Bit2 - KeyB - Authenticate with KeyA or Key B. 0/1= KeyA/KeyB.
	 * @param blk_add Start address of memory blocks to read.
	 * @param num_blk Number(1 -4 ) of block to read.
	 * @param snr The serial number (UID), which will be ignored if the SNR bit is not set, of the card to be selected. The serial number (UID) must be "Big-Endian" mode, 
	 * for example, if you have a Mifare card with UID "1A2B3C4D", when you call MF_HLRead() or MF_HLWrite(), 
	 * the 5tharameter, snr must be "4D3C2B1A", The serial number of the card selected will be return by the snr after the function is called successfully.
	 * @param buffer Pointer to the buffer which returns the data read from the card. The length of the buffer equals to num_blk *16 bytes.
	 * @return 0 - Successful.
	 */
	public native static int MF_HLRead(int DeviceAddress,byte mode,	byte blk_add,byte num_blk, byte[] snr, byte[] buffer);
	
	/**
	 * Write multiple (up to four) blocks data to the Mifare card. 
	 * Note:The blocks to be written must be in the same sector. Writing data to the sector trailer (block address =  N*4+3, 
	 * where N is the sector number) should be handled carefully, otherwise you may corrupt the KEY area and lock the sector. 
	 * Before calling, the 2 APIs must be called firstly:MF_StoreKeyToEE() and LoadKeyFromEE(), the 2 APIs used to provide correct Keys to save into Reader, 
	 * being used for authentication.
	 * @param DeviceAddress Device Address of the reader.
	 * @param mode Operating Mode.
	 * Bit0 - All - Select Request all or Request IDLE mode (0/1= IDLE/ALL).
	 * Bit1 - SNR - Enable the Card Serial Number comparison features. The further operating will be processed only if the detected card number matches the card serial number sent by the Host.
	 * Bit2 - KeyB - Authenticate with KeyA or Key B. 0/1= KeyA/KeyB. 	
	 * @param blk_add Starting block address of memory blocks to be written.
	 * @param num_blk Number(1 -4 ) of block to write.
	 * @param snr The serial number (UID), which will be ignored if the SNR bit is not set, of the card to be selected.
	 * The serial number (UID) must be "Big-Endian" mode, for example, if you have a Mifare card with UID "1A2B3C4D",
	 * when you call MF_HLRead() or MF_HLWrite(), the 5tharameter, snr must be "4D3C2B1A",The serial number of the card selected will be return by the snr after the function is called successfully.
	 * @param buffer Pointer to the buffer which stores the data to be written to the card. The length of the buffer equals to num_blk *16 bytes.
	 * @return 0 - Successful.
	 */
	public native static int MF_HLWrite(int DeviceAddress,byte mode, byte blk_add,byte num_blk, byte[] snr, byte[] buffer);
	
	/**
	 * Initials the value block (Block 1) and the back-up block (Block 2) to the MIFARE VALUE BLOCK format with the initial value. Before calling, the 2 APIs must be called firstly:
	 * MF_StoreKeyToEE() and LoadKeyFromEE(), the 2 API use to provide correct Keys to save into Reader, being used for authentication.
	 * @param DeviceAddress Device Address of the reader.
	 * @param mode Operating Mode.
	 * Bit0 - All - Select Request all or Request IDLE mode (0/1= IDLE/ALL).
	 * Bit1 - SNR - Enable the Card Serial Number comparison features. The further operating will be processed only if the detected card number matches the card serial number sent by the Host.
	 * Bit2 - KeyB - Authenticate with KeyA or Key B. 0/1= KeyA/KeyB.
	 * @param SectNum The number of the sector to be formatted as value block.
	 * @param snr The serial number, which will be ignored if the SNR bit is not set, of the card to be selected.
	 * The serial number of the card selected will be return by the snr after the function is called successfully.
	 * @param value The initial value to be written to the value blocks.
	 * @return 0 - Successful
	 */
	public native static int MF_HLInitVal(int DeviceAddress,byte mode,byte SectNum, byte[] snr, int value);
	
	/**
	 * Decrease the value of a MIFARE Value Block and also copy the value block to the backup block.
	 * Before calling, the 2 APIs must be called firstly:
	 * MF_StoreKeyToEE() and LoadKeyFromEE(), the 2 API use to provide correct Keys to save into Reader, being used for authentication.
	 * @param DeviceAddress Device Address of the reader.
	 * @param mode Operating Mode.
	 * Bit0 - All - Select Request all or Request IDLE mode (0/1= IDLE/ALL).
	 * Bit1 - SNR - Enable the Card Serial Number comparison feature. The further operating will be processed only if the detected card number matches the card serial number sent by the Host.
	 * Bit2 - KeyB - Authenticate with KeyA or Key B. 0/1= KeyA/KeyB.
	 * @param SectNum The number of the sector to be formatted as value block.
	 * @param snr The serial number, which will be ignored if the SNR bit is not set, of the card to be selected.
	 * The serial number of the card selected will be return by the snr after the function is called successfully.
	 * @param value The value to be increment.
	 * @return 0 - Successful.
	 */
	public native static int MF_HLDec(int DeviceAddress,byte mode,byte SectNum, byte[] snr, int[] value);
	
	/**
	 * Increase the value of a MIFARE Value Block and also copy the value block to the backup block.
	 * Before calling, the 2 APIs must be called firstly:MF_StoreKeyToEE() and LoadKeyFromEE(), the 2 API use to provide correct Keys to save into Reader, being used for authentication.
	 * @param DeviceAddress Device Address of the reader.
	 * @param mode Operating Mode.
	 * Bit0 - All - Select Request all or Request IDLE mode (0/1= IDLE/ALL).
	 * Bit1 - SNR - Enable the Card Serial Number comparison features. The further operating will be processed only if the detected card number matches the card serial number sent by the Host.
	 * Bit2 - KeyB - Authenticate with KeyA or Key B. 0/1= KeyA/KeyB.
	 * @param SectNum The number of the sector to be formatted as value block.
	 * @param snr The serial number, which will be ignored if the SNR bit is not set, of the card to be selected.
	 * The serial number of the card selected will be return by the snr after the function is called successfully.
	 * @param value The value to be increment.The content of the value block will be returned after the increment is done.
	 * @return 0 - Successful.
	 */
	public native static int MF_HLInc(int DeviceAddress,byte mode,byte SectNum, byte[] snr, int[] value);
	
	/**
	 * The MF_HLRequest command integrated the low level commands - Request, 
	 * Anti-Collision - Select(AntiColl2- Seledt2, AntiColl3-Select) to a single one-step operation.
	 * @param DeviceAddress Device Address of the reader.
	 * @param mode Operating Mode.
	 * Bit0 - All - Select Request all or Request IDLE mode (0/1= IDLE/ALL).
	 * Bit1 - SNR - Enable the Card Serial Number comparison features. The further operating will be processed only if the detected card number matches the card serial number sent by the Host.
	 * @param len Number(4 -10 byte) of UID.
	 * @param snr Pointer to a 4-byte(or 7-byte, 10-byte ) buffer storing the UID(card serial number) of the card to be selected.
	 * @return 0 - Successful.
	 */
	public native static int MF_HLRequest(int DeviceAddress, byte mode, int[] len, byte[] snr);
	
	/**
	 * General command to access with card
	 * @param DeviceAddress Device Address of the reader.
	 * @param mode 0x00 - After send the command successfully, don't receive the data from reader.
	 * 0x01 -After send the command successfully, receive the data from reader.
	 * @param buffer buffer[0]	Command.
	 * buffer[1]	data length(should contain timeout).
	 * buffer[2]	time out.
	 * buffer[3...n]	data.
	 * @param Numbyte Length of buffer - 2.
	 * @return 0 - Successful.
	 */
	public native static int GenericCMD(int DeviceAddress,byte mode, byte[] buffer, int[] Numbyte);
	
	/**
	 * Setup the default  WiegandMode when the read poweron.
	 * If WiegandMode is active,the Reader will request card continue by itself.
	 * When it find a card, the card serial number will be send out by Rs232 and Wiegand Interface.
	 * @param DeviceAddress Device Address of the reader.
	 * @param status Bit4: 1/0 = Active/Deactive WiegandMode when the reader power on.
	 * Bit1: 1/0 = Enable/Disable Auto indication by LED&Buzzer.
	 * Bit0: 1/0 = Enable/Disable ExtPin control LED & Buzzer
	 * @return 0 - Successful.
	 */
	public native static int SetWiegandStatus(int DeviceAddress,byte status); 
	
	/**
	 * Get the setting data for Wiegand mode.
	 * @param DeviceAddress Device Address of the reader.
	 * @param reqbuffer Refer to the definition of WiegandMode function.
	 * @return 0 - Successful.
	 */
	public native static int GetWiegandMode(int DeviceAddress,byte[] reqbuffer);
	
	/**
	 * Active the WiegandMode but not change the default Poweron_ Wiegand_ Status.
	 * @param DeviceAddress
	 * @param status Bit4: 1/0 = Active/Deactive WiegandMode.
	 * Bit1: 1/0 = Enable/Disable Auto indication by LED&Buzzer.
	 * Bit0: 1/0 = Enable/Disable ExtPin control LED & Buzzer.
	 * @return 0 - Successful.
	 */
	public native static int ActiveWiegandMode(int DeviceAddress,byte status);
	
	/**
	 * Set Wiegand (automatically read) output options  of the reader (valid for CV5100/5500/5600, CV6X01, CN3XX, CN6XX)
	 * @param DeviceAddress Device Address of the reader
	 * @param data Wiegand setting data.
		DATA[0]:   Wiegand  output format
		0x00    wiegand_26
		0x01    wiegand_32
		0x02    wiegand_40
		0x03    wiegand_34
		0x04    wiegand_42
		0x05    wiegand_32e
		0x06    wiegand_56
		0x20    ABA Tack1
		0x21    ABA Tack2
		0x22    ABA_EP_3 (C & D 3 BYTE  for Europe Sandard)
		0x23    ABA_EP_4 (C & D 4BYTE  for Europe Sandard)
		DATA[1]:   Block number of Wiegand data stored on the Mifare card
		0    basic mode, output serial number (UID).
		1~63    sector mode output the first 4 bytes of data block in Wiegand format, key to access is needed, and the key must previously be saved in reader via the API MF_StoreKeyToEE(). Note: the API MF_StoreKeyToEE() must be called before the API WiegandMode()
		DATA[2]:   REQUEST mode
		0x26    IDLE
		0x52    ALL
		DATA[3]:   Wiegand Flag
		bit0:   LED and BUZZER controlled by external I/O, 1-enable 0-disable
		                bit1:   LED and BUZZER auo-larm after read successfully, 1-enable 0-disable
		bit4:   Enable Wiegand (automatically read card), 1-enable 0-disable
		DATA[4]:   Key format
		0x00    8-bit keypad format
		0x01    4-bit keypad format
		DATA[5]:   output select
		bit0:   card serial number output direction, 1-serial number reversed will be outputted, 0-normal serial number will be outputted, the default value is 0. E.g bit0=1 D649C3A0; bit0=0A0C349D6.
		bit1:   UART port output enable, 1-enable 0-disable
		Note: for the type CV6X01, if bit1=0, USB port output is enable.
		bit2:   Press key beep, 1-enable 0-disable
		bit3~bit4:   configure the data output format
		
		---------------------------------------------------------------------------------------------------------------------------------------------------------------------
		-	bit3	-	bit4	-	Output format			-	Description																									-
		---------------------------------------------------------------------------------------------------------------------------------------------------------------------
		-	0		-	0		-	HEX+Protocol(default)	-	data output format is in HEX mode with our  communication protocol (protocol header+HEX data+protocol tail)	-
		---------------------------------------------------------------------------------------------------------------------------------------------------------------------
		-	0		-	1		-	HEX						-	data output format is in HEX mode																			-
		---------------------------------------------------------------------------------------------------------------------------------------------------------------------
		-	1		-	0		-	ASCII					-	data output format is in ASCII mode																			-
		---------------------------------------------------------------------------------------------------------------------------------------------------------------------
		-	1		-	1		-	DEC-ASCII				-	1.Convert to decimalis	2.Output format is in ASCII mode													-
		---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
		DATA[6]:   Block number of Wiegand data stored on the ISO15693 card.
		0XFF    basic mode, output card Inventory.
		DATA[7]	  Card Type
					  0x00     Mifare 1
					  0x01     Mifare Plus
					  0x02     Mifare DESFire EV1
		DATA[8...9]:   
		RESERVE must be set to 0x00
	 * @return 0 - Successful.
	 */
	public native static int WiegandMode(int DeviceAddress,byte[] data);

	/**
	 * DesFire card's authentication procedure with DES/3DES, 3Key 3DES or AES, in this procedure, both the DesFire card as well as the reader, show in an encrypted way that they posses the same secret which especially means the same key. This procedure not only confirms that both DesFire card and reader can trust each other but also generates a session key which can be used to keep the further communication path secure Each time a new authentication procedure is successfully completed a new key for further cryptographic operations is obtained. Depending on the configuration of the application (represented by its AID), an authentication has to be done to perform specific operations: 
	Gather information about the application.
	Change the keys of the application.
	Create and delete file within the application.
	Change access rights. 
	Access data files in the authenticated application.
	Depending on the security configuration of the DesFire card, the following commands may require an authentication with the DesFire card master keys:
	Gather information about the applications on the DesFire card.
	Change the DesFire card master key itself.
	Change the DesFire card key settings.
	Create a new application.
	Delete an existing application
	The authentication state is invalidated by:
	Selecting an application.
	Changing the key which was used for reaching the currently valid authentication status.
	A failed authentication
	Please note: Master keys are identified by their key number 0. This is valid on 
	Card (selected AID=0) and on application level (selected AID 1-28).
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously 
	(selected by a single reader), this CID is assigned by RATS(), the CID number must be in the range 0-14.
	 * @param CryptoType The crypto type to authenticate with, 0-DES/3DES, 1-3Key 3DES, 2-AES 
	 * @param KeyNo The key number passed to the DesFire card in order to select a certain key stored in it (up to 14 different keys per application). If the key number does not reflect a valid key in the DesFire card, an error code is sent by the card.
	 * @param Key The 16-bytes keys (DES/3DES and AES) or 24-bytes keys (3Key 3DES) for the authentication procedure. A successful verification proves to the DesFire card that the card and the reader posses the same key
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_Authenticate(int DeviceAddress,byte CID,byte CryptoType, byte KeyNo, byte[] Key);
	/**
	 * This API changes the master key configuration settings depending on the currently   
selected AID. If AID=0 has been selected in advance. The change applies to DesFire card key settings, otherwise (AID: 1-15) it applies to the application key settings of the currently selected application. Additionally a successful preceding authentication with the master key is required(Card master key if AID=0, else with application master key).
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously  
(selected by a single reader), this CID is assigned by RATS(), the CID number must be 
in the range 0-14.
	 * @param MasterKeySettings Card master key settings, on card level (selected AID=0) the coding is interpreted as:
Bit7-Bit4: RFU, has to be set to 0
Bit3: codes whether a change of the card master key settings is allowed:
0: configuration not changeable anymore (frozen).
1: this configuration is changeable if authenticated  with card master key(default     setting)
Bit2: codes whether card master key authentication is needed before Create/Delete 
Application
 0: CreateApplication/DeleteApplication is permitted only with card master key   
authentication.
 1: CreateApplication is permitted without card master key authentication(default setting)
Bit1: codes whether card master key authtentication is needed for application directory 
access:
0: Successful card master key authentication is required for executing the 
GetApplicationIDs  and GetKeySettings commands. 
1: GetApplicationIDs and GetkeySettings commands succeed independently of a 
preceding card master key authentication (default setting).
Bit0: Codes whether the card master key is changeable:
0: card master key is not changeable anymore(frozen).
 1: card master key is changeable (authentication with the current card master key necessary default setting)
Card master key settings, on card level (selected AID=1-15) the coding is interpreted as:
Bit7-Bit4: hold the access rights for changing application keys(ChangeKey API)
0: Application master key authentication is necessary to change any key (default)
1..13: Authentication with the specified key is necessary to change any key.
14: Authenticatoin with the key to be changed (same keyno) is necessary to change a key
15: All keys(except application master key, see Bit0), within this application are frozen.
Bit3: codes whether a change of the application master key settings is allowed:
0: configuration not changeable anymore (frozen).
1: this configuration is changeable if authenticated  with application master key(default setting)
Bit2: codes whether application master key authentication is needed before Create/Delete 
File
0: CreateFile / DeleteFile is permitted only with application master key authentication.
 1: CreateFile is permitted without application master key authentication (default setting)
Bit1: codes whether application master key authentication is needed for file directory access:
0: Successful application master key authentication is required for executing the 
GetFileIDs, GetFileSettings and GetKeySettings API. 
1: GetFileIDs, GetFileSettings and GetKeySettings API succeed independently of a 
preceding application master key authentication (default setting).
Bit0: Codes whether the application master key is changeable:
0: application master key is not changeable anymore(frozen).
1: application master key is changeable (authentication with the current card master key necessary default setting)
-
Remark: in case of usage of the application master key for deletion, the application which is about to be deleted needs to be selected and authenticated with the application master key prior to the DeleteApplication API.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_ChangeKeySettings(int DeviceAddress,byte CID,byte MasterKeySettings);
	/**
	 * This API allows to get configuration information on the DesFire card and application 
	master key configuration settings as described in 'MF_DFEV1_ChangeKeySettings', in addition it returns the maximum number of keys witch can be stored within the selected application. Depending on the master key settings, a preceding authentication with the master key is required. If the card master keys are queried (currently selected AID=0), the number of keys is returned as 1, as only one card master key exists on a card.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a    single reader), this CID is assigned by RATS(), the CID number 
	must be in the range 0-14.
	 * @param MasterKeySettings Master key configuration settings as described in 'MF_DFEV1_ChangeKeySettings'.
	 * @param KeyNumber Maximum number of keys witch can be stored within the selected application
	 * @param CryptoType Crypto type of key authenticate, 0-DES/3DES, 1-3Key 3DES, or 2-AES, it is always 0 (DES/3DES) for MF31CD40 card
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_GetKeySettings(int DeviceAddress,byte CID,byte[] MasterKeySettings,byte[] KeyNumber,byte[] CryptoType);
	
	/**
	 * This API allows to change any key stored card (AID=0) or application (AID is not 0). If AID=0 is selected, the change applies to the card master key and therefore only KeyNo=0 is valid (only one card master key is present on a card). In all other cases (AID is not 0) the change applies to the specified KeyNo within the currently selected application (represented by it's AID).
	               Remark: After a successful change of the key used to reach the current authentication status, this authentication is invalidated i.e. an authentication with the new key is necessary for subsequent operations.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a    single reader), this CID is assigned by MF_DF_RATS() or 
	MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param KeyNo The key to be changed, which has to be in the range from 0 to number of application keys-1. 
	 * @param CryptoType Crypto type of key authenticate, 0-DES/3DES, 1-3Key 3DES, or 2-AES, it is always 0 (DES/3DES) for MF31CD40 card 
	 * @param KeyVersion A version of Key, valid for only AES crypto type.
	 * @param NewKey The new key, for DES/3DES and AES it is 16 bytes, but the send data length is 24bytes, So should padding 8bytes(0x00);  for 3Key 3DES, it is 24 bytes.
	 * @param OldKey The old key of the KeyNo, in case the KeyNo used for authentication is different from the KeyNo to be changed, the old_Key is necessary.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_ChangeKey(int DeviceAddress,byte CID,byte KeyNo, byte CryptoType, byte KeyVersion, byte[] NewKey, byte[] OldKey);
	/**
	 * This API allows to read out the current key version of any key stored on the card or application. If AID=0 is selected, it returns the version of the card. If AID=0 is selected, the API returns the version of the card master key and therefore only KeyNo=0 is valid, only one card master key is present on a card. In all other cases (AID is not 0) the version of the specified KeyNo  within the currently selected application(represented by it's AID) is returned. This API can be issued without valid authentication.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a    single reader), this CID is assigned by MF_DF_RATS() or 
	MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param KeyNo The key which's version to be read.
	 * @param KeyVersion The version returned (one byte).
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_GetKeyVersion(int DeviceAddress,byte CID, byte KeyNo,byte[] KeyVersion);
	/**
	 * The API allows creating new application on the card. Depending on the card master key settings, a preceding card master key authentication may be required.  
	               Remark: It is strongly recommended to personalize the keys latest at card issuing using the API 'ChangeKey'.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a    single reader), this CID is assigned by MF_DF_RATS() or 
	MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param AID An application is identified by an 'Application Identifier', AID, which is implemented as a 24 bit number. Application Identifier 0 is reserved as a reference to the card itself. One card can hold up to 28 Application.
	 * @param MasterKeySettings The application master key settings which defined in API 'MF_DF_ChangeKeySettings'.
	 * @param KeyNum The parameter defines how many keys can be used by the application for cryptographic purposes. Each application is linked to a set of up to 14 different user definable access keys. All keys are initialized with a string consisting of sixteen 0 bytes. 
	 * @param CryptoType Crypto types of key authenticate, 0-DES/3DES, 1-3Key 3DES, or 2-AES, it is always 0 (DES/3DES) for MF31CD40 card, the CryptoMethod unchangeable after you create an application, that means you must select authentication type at creation of application in future, and it can't be changed in future.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_CreateApplication(int DeviceAddress,byte CID, int AID, byte MasterKeySettings, byte KeyNum, byte CryptoType);
	/**
	 * The API allows to permanently deactivating an application on the card. Depending on the application master key settings a preceding master key authentication is required. If the currently selected application is deleted, this API automatically selects the card level, selected AID=0.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or 
	MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param AID The application to be deleted.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_DeleteApplication(int DeviceAddress,byte CID, int AID);
	/**
	 * The API returns the application identifiers of all active application on a card. This API required that the currently selected AID is 0 which references the card level. Depending on the card (AID=0) master key settings, a successful authentication with the card (AID=0) master key might be required to execute this API
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a    single reader), this CID is assigned by MF_DF_RATS() or 
	MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param AIDs The buffer to save application identifiers returned from the card, it is an array of integer, it is up to 27 AID. 
	 * @param AIDNum the returned number of application saved in current card.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_GetApplicationIDs(int DeviceAddress,byte CID, int[] AIDs, int[] AIDNum);
	/**
	 * The API allows selecting on specific application for further. If AID=0, the card level is selected and any operations, typically API like 'CreateApplication', 'DeleteApplication'... are related to this level. If an application with the specified AID is found in the application directory of the card, the subsequent API interact with this application.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a    single reader), this CID is assigned by MF_DF_RATS() or 
	MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param AID The application to be selected.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_SelectApplication(int DeviceAddress,byte CID, int AID);
	/**
	 * The API releases the card user memory. All applications are deleted and all files within those applications are deleted. The card master key and the card master key settings keep their currently set values; they are not influenced by this API. This API always requires a preceding authentication with the card master key.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_FormatPICC(int DeviceAddress,byte CID);
	/**
	 * The API returns manufacturing related data of the card.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param VersionInfo The version returned contains 28 bytes:
	The first 7 bytes contains hardware related information:
	No.1 byte: codes the vendor ID (4 for NXP)
	No.2 byte: codes the type 
	No.3 byte: codes the subtype 
	No.4. byte: codes the major version number
	No.5 byte: codes the minor version number
	No.6 byte: codes the storage size
	No.7 byte: codes the communication protocol type
	The next 7 bytes contains software related information:
	No.8 byte: codes the vendor ID (4 for NXP)
	No.9 byte: codes the type 
	No.10 byte: codes the subtype 
	No.11. byte: codes the major version number
	No.12 byte: codes the minor version number
	No.13 byte: codes the storage size
	No.14 byte: codes the communication protocol type
	The last 14 bytes contains the unique serial number, batch number, year and 
	calendar week of production:
	No.15 byte-No.21 byte: code the unique serial number
	No.22 byte-No.26 byte: code the production batch number
	No.27 byte: codes the calendar week of production
	No.28 byte: codes the year of production. 
	 * @param VersionInfoLen 
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_GetVersion(int DeviceAddress,byte CID, byte[] VersionInfo,int[] VersionInfoLen);
	/**
	 * The API return the remain memory (bytes). The API is invalid to MF31CD40 card.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   (selected   by a    single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param RemainMemSize The remain memory to be used (bytes).
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_GetFreeMem(int DeviceAddress,byte CID, int[] RemainMemSize);
	/**
	 * The API return the UID of card. The API is invalid to MF31CD40 card.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param UID the UID returned
	 * @param UIDLen
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_GetCardUID(int DeviceAddress,byte CID, byte[] UID,int[] UIDLen);
	/**
	 * The API configure card. The API is invalid to MF31CD40 card.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or 
	MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param ConfigType define the following pConfigurationData type
	                0-Configuration, 1- Key and Key version, 2-User ATS.
	 * @param ConfigData if ConfigType is 0-configuration the ConfigData is 1 byte and its bits  means 
	            bit0: 0-The API FormatPICC() enable; 1-Disabled, by default bit0=0, if you set to 1, it will never be resented to 0 any more.
	            Bit1: 1-The Random ID enable; 0- Disabled, by default bit0=0, if you set to 1, it will never be resented to 0 any more, if Random ID enable, the MF_DF_AnticollL1() will return a random UID and the random UID is different from every powering on the reader, also, the MF_DF_AnticollL2() and MF_DF_SelectL2() are not need, after MF_DF_SelectL1() step you can call MF_DF_RATS() or MF_DF_RATSEx(). For getting the original UID you can call the API MF_DF_GetCardUID(), this UID is unchangeable.
	               	if ConfigType is 1- Key and Key version ConfigData is 25 bytes, 24 are  3Key 3DES 
	Key, and other 1 byte is the key version, the key and key version is the default 
	key and key version after you create a new application, the left 16 bytes is for the 
	DES/3DES and AES key.
	               	if ConfigType is 2-User ATS, ConfigData will be ATS as you want. The API 
	MF_DF_RATSEx() will return the ATS defined here.
	 * @param ConfigDataLen
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_SetConfiguration(int DeviceAddress,byte CID, byte ConfigType, byte[] ConfigData,int ConfigDataLen);
	/**
	 * The API returns the file identifiers of all active files within the currently selected    application. Depending on the application master key settings, preceding authentication with the application master key might be required.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID he logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or 
	MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileIDs The buffer to save file identifiers of all active files within the currently selected 
	application. Duplicate values are not possible as each file must have an unambiguous 
	identifier.
	 * @param FileIDsLen The parameter is used to save the number of files within the currently selected application.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_GetFileIDs(int DeviceAddress,byte CID, byte[] FileIDs,int[] FileIDsLen);
	/**
	 * The API allows getting information on the properties of specific file. The information provided by this API depends on the type of the file which is queried. Depending on 
	the application master key settings, a preceding authentication with the application 
	master key might be required.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The file number of the file to be queried within the currently selected application..
	 * @param FileSettings The buffer to save the file settings of file to be read within the currently selected 
	application. The first 5 bytes is the same for all file types:
	No.1 byte: Codes the file type:
	          0: Standard Data File; 
	1: Backup Data File; 
	2: Value File; 
	3: Linear Record File; 
	4: Cycle Record File;
	No.2 byte: Codes the communication settings. Data transmission between card and reader can be done on 3 levels of security. When you read data from card or write data to file, you need to know communication settings:
	      	0: Plain; 
	1: MACing; 
	3: DES/3DES Full Enciphered; 
	No.3 byte and No.4 byte: codes the Access Rights of the file. Access to user data is granted on application level. For each application up to 14 different user definable keys can be assigned to control access to data stored in the card. There are 4 different Access Rights stored for each file within each application:
	Read Access: GetValue, Debit For Value Files
	Write Access: GetValue, Debit, LimitedCredit For Value Files
	Read & Write Access: GetValue, Debit, LimitedCredit, Credit For  Value Files
	Change Access Rights: 
	No.4 byte	No.3 byte

	Each of the Access Rights is coded in 4 bits, one nibble. Each nibble represents a link to one of the keys stored within the respective application's key file. One nibble allows to code 16 different values. If such a value is set to a number between 0 and 13 (Max. 14 Keys), this references a certain key within the application's key file, provided that the key exists (selecting a non-existing key is not allowed). If the number is coded as 14, this means 'Free' Access. Thus the regarding access is granted always with and without a preceding authentication, directly after the selection of the respective application. The number 15 define the opposite of  'Free' access and has the meaning 'Deny' access. Therefore the respective linked Access Rights is always denied. The least significant nibble holds the reference number of the key, which is necessary to be authenticated with in order to change the Access Rights for the file and to link each Access Right to key numbers.
	All subsequent bytes have a special meaning depending on the file type:
	Standard Data File and Backup Data Files: 
	1 field of three bytes length returns the user file size in byte.
	Value File: 
	4 fields, each of 4 bytes length, are returned whereby the first field returns the 'Lower Limit' of the file (as defined at file creation), the second field returns the 'Upper Limit'  (as defined at file creation) and the next field returns the current maximum 'Limited Credit' Value. If the limited credit functionality is not in use, the last field contains all zeros. The last byte codes, if the 'limitedCredit' API is allowed for this file(0 for disabled, 1 for enabled)
	Linear Record File and Cyclic Record Files:
	3 fields, each of three bytes length, are returned whereby the first field codes the size of one single record (as defined at file creation), the second field codes the maximum number of records within the record file (as defined at file creation) and the last field returns the current number of records within the record file. This number equals the maximum number of records which currently can be read.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_GetFileSettings(int DeviceAddress,byte CID,byte FileID, byte[] FileSettings);
	/**
	 * The API changes the access parameters of an existing file.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or 
	MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The file number of the file to be queried within the currently selected application. This 
	file number must be in the range between 0 and 15.
	 * @param CmtSet new Communication Settings.
	 * @param AccessRights New AccessRights. 
	 * @param Is_Rights_Free If the 'ChangeAccessRights' access rights is set with the value 'Free', no security 
	mechanism is necessary.
	 * @return 0 - Successful
	 */
	public native static int  MF_DFEV1_ChangeFileSettings(int DeviceAddress,byte CID,byte FileID,byte CmtSet,short AccessRights,boolean Is_Rights_Free);
	/**
	 * The API is used to create a standard file for the storage of plain unformatted user data 
	within an existing application on card.
	Remark: The DesFire Card internally allocates memory in multiples of 32 bytes. Therefore a file creation API with FileSize parameter 1 (1 byte file size) will internally consume the same amount of memory as 32 (32 byte file size), namely 32 bytes.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   (selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The file number of the new standard file. The file will be created in the currently 
	selected application. It is not necessary to create the files within the application in a 
	special order. If a file with the specified number already exists within the currently selected application, an error code is returned. 
	 * @param FileType 0-standard data file, 1-backup data file.
	 * @param CmtSet Communication Settings as defined in API 'MF_DF_GetFileSettingsEx'.
	 * @param AccRights AccessRights as defined in API 'MF_DF_GetFileSettingsEx'. 
	 * @param CreateSize The size of the standard file to be created.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_CreateDataFile(int DeviceAddress,byte CID,byte FileID,byte FileType, byte CmtSet,short AccRights,int CreateSize);
	
	/**
	 * The API is used to create a value file for the storage and manipulation of 32bit signed 
	integer values within an existing application on the card. ValueFiles feature always the integrated backup mechanism. Therefore every access changing the value needs to be validated using the CommitTransaction() API.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The file number of the new value file. The file will be created in the currently selected 
	application. It is not necessary to create the files within the application in a special 
	order. If a file with the specified number already exists within the currently selected 
	application, an error code is returned. 
	 * @param CmtSet Communication Settings as defined in API 'MF_DF_GetFileSettingsEx()'.
	 * @param AccRights AccessRights as defined in API 'MF_DF_GetFileSettingsEx()'. 
	 * @param LLimit The lower limit marks the limit which must not be passed by a Debit calculation on the 
	current value. The lower limit is a 4 byte signed integer and thus may be negative too.
	 * @param ULimit The upper limit use to code the upper limit which sets the boundary in the same 
	manner but for the Credit operation,. The lower limit is a 4 byte signed integer and thus 
	may be negative too. The upper limit has to be lower then the lower limit, otherwise an 
	error message would be sent by the card and thus the file would not be created.
	 * @param InitVal The parameter is a 4 byte signed integer and specifies the initial value of the value file.
	The upper and lower limit is checked by the card, in case of inconsistency the file is not 
	created and an error message is sent by the card. 
	 * @param LimitCredit The parameter codes the activation of the LimitedCredit feature, Here 0 means that 
	LimitedCredit is disabled and 1 enables this feature.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_CreateValueFile(int DeviceAddress,byte CID,byte FileID,byte CmtSet, short AccRights,int LLimit,int ULimit,int InitVal,byte LimitCredit);
	/**
	 * The API is used to create files for multiple storage of structural data, for example for 
	loyalty programs, within an existing application on the card. Once the file is filled 
	completely with data records, further writing to the file is not possible unless it is 
	cleared. record files feature always the integrated backup mechanism. Therefore every access appending a record needs to be validated using the CommitTransaction().

	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The file number of the new linear record file. The file will be created in the currently 
	selected application. It is not necessary to create the files within the application in a 
	special order. If a file with the specified number already exists within the currently 
	selected application, an error code is returned. 
	 * @param FileType 0- Linear record file, 1-cyclic record file.
	 * @param CmtSet Communication Settings as defined in API 'GetFileSettings ()'.
	 * @param AccRights AccessRights as defined in API 'GetFileSettings()'.
	 * @param SingleRecordSize The parameter codes the size of one single record in bytes.
	 * @param RecordNumber The parameter codes the number of records.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_CreateRecordFile(int DeviceAddress,byte CID,byte FileID,byte FileType,byte CmtSet, short AccRights,int SingleRecordSize,int RecordNumber);
	/**
	 * The API permanently deactivates a file within the file directory of the currently selected application. The operation of this API invalidates the file directory entry of the specified file which means that the file can't be accessed anymore. Depending on the application master key settings, a preceding authentication with the application master key is required. Allocated memory blocks associated with the deleted file are not set free. The FileNo of the deleted file can be re-used to create a new file within that application
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The file to be deleted.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_DeleteFile(int DeviceAddress,byte CID, byte FileID);
	/**
	 * The API allows to read data from Standard Data Files or Backup Data Files. If backup Data Files are read after writing to them, but before issuing the CommitTransactionEx() API the ReadData API will always retrieve the old, unchanged data stored in the card, All data written to a Backup Data File is validated and externally 'visible' for a ReadData API only after a CommitTransaction() API. The ReadData API requires a preceding authentication either with the key specified for 'Read' or 'Read & Write' Access Rights. Depending on the communication settings linked to the file, data will be sent by the card either plain, MACed or full enciphered.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously   
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The file number to be read from.
	 * @param CmtSet Communication Settings as defined in API 'GetFileSettings()'. The parameter must be 
	same as the communication settings linked to the file.
	 * @param Offset The parameter codes the starting position for the read operation within the file. This 
	parameter has to be in the range from 0 to file size -1.
	 * @param ReadLength The parameter specifies the number of data bytes to be read. If the parameter is coded 
	as 0, the entire data file, starting from the position specified in the offset value is read.
	 * @param ReturnData The buffer to save the data read from the card.
	 * @param ReturnDataLen The length of data in bytes read from the card if the API successfully.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_ReadData(int DeviceAddress,byte CID,byte FileID,byte CmtSet, int Offset,int ReadLength,byte[] ReturnData,int[] ReturnDataLen);
	/**
	 * The API allows to write data to Standard Data Files and Backup Data Files. If this API is performed on a Backup Data File, it is necessary to validate the written data with a CommitTransactionEx() API. An AbortTransactionEx() API will invalidate all changes. If data is written to Standard Data Files (without integrated backup mechanism), data is directly programmed into the file. The new data is immediately available to any following ReadData API performed on the file. This API requires a preceding authentication either with the key specified for 'Write' or 'Read & Write' Access Rights. Depending on the communication settings linked to the file, data needs to be sent by the reader either plain, MACed or full Enciphered.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The file number to be written to.
	 * @param CmtSet Communication Settings as defined in API 'MF_DF_GetFileSettingsEx()'. The 
	parameter must be same as the communication settings linked to the file.
	 * @param Offset The parameter codes the starting position for the write operation within the file. This 
	parameter has to be in the range from 0 to file size -1.
	 * @param WriteDataLength The parameter specifies the number of data bytes to be written.
	 * @param WriteData The buffer of data to be written to the file. 
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_WriteData(int DeviceAddress,byte CID,byte FileID,byte CmtSet, int Offset,int WriteDataLength,byte[] WriteData);
	/**
	 * The API allows to read the currently stored value from Value Files. After updating a value file's value but before issuing the CommitTransactionEx() API, the GetValue API will always retrieve the old, unchanged value which is still the valid one. This API requires a preceding authentication with the key specified for 'Read', 'Write' or 'Read & Write' Access Rights.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The value file number to read from.
	 * @param CmtSet Communication Settings as defined in API 'MF_DF_GetFileSettingsEx()'. The 
	parameter must be same as the communication settings linked to the file.
	 * @param RetValue The current value of the value file returned if this API successfully.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_GetValue(int DeviceAddress,byte CID, byte FileID, byte CmtSet, int[] RetValue);
	/**
	 * The API allows to increase a value stored in a value file. It increases the current value 
	stored in the file by a certain amount. Depending on the communication settings the incValue is either transferred in plain, MACed or full Enciphered. It is necessary to validate the updated value with a CommitTransactionEx() API. An AbortTransactionEx() API will invalidate all changes. The value modifications of Credit, Debit and LimitedCredit APIs are cumulated until a CommitTransactionEx() API is issued. Credit API never modify the LimitedCredit value of a value file. However, if the LimitedCredit value needs to be set to 0, a LimitedCredit with value 0 can be used. This API requires a preceding authentication with the key specified for 'Read & Write ' Access Rights.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The value file number to be increased.
	 * @param CmtSet Communication Settings as defined in API 'GetFileSettings()'. The parameter must be 
	same as the communication settings linked to the file.
	 * @param IncValue A certain amount to be increased on the value stored in the file, Only positive values 
	are allowed for this API.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_Credit(int DeviceAddress,byte CID, byte FileID, byte CmtSet,int IncValue);
	/**
	 * The API allows a limited increase a value stored in a value file without having full 
	'Read & Write' permissions to the file. This feature can be enabled or disabled during value file creation. It increases the current value stored in the file by a certain amount. Depending on the communication settings the incValue is either transferred in plain, MACed or Full Enciphered. It is necessary to validate the updated value with a CommitTransactionEx() API. An AbortTransactionEx() API will invalidate all changes. The value modifications of Credit, Debit and LimitedCredit APIs are cumulated until a CommitTransactionEx() API is issued. The parameter incValue for LimitedCredit API is limited to the sum of the Debit API on the value file within the most recent transaction containing at least one Debit. After executing the LimitedCredit API the new limit is set to 0 regardless of the amount which has been re-booked. There fore the LimitedCredit API can only be used once after a Debit transaction. This API requires a preceding authentication with the key specified for 'Write' or 'Read & Write ' Access Rights
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The value file number to be increased.
	 * @param CmtSet Communication Settings as defined in API 'MF_DF_GetFileSettings'. The parameter 
	must be same as the communication settings linked to the file.
	 * @param IncValue Acertain amount to be increased on the value stored in the file, Only positive values 
	are allowed for this API.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_LimitedCredit(int DeviceAddress,byte CID, byte FileID, byte CmtSet,int IncValue);
	/**
	 * The API allows to decrease a value stored in a value files. Depending on the communication settings the decValue is either transferred in plain, MACed or full enciphered. It is necessary to validate the updated value with a CommitTransactionEx() API. An AbortTransactionEx() API will invalidate all changes. The value modifications of Credit, Debit and LimitedCredit APIs are cumulated until a CommitTransactionEx() API is issued. If the usage of the LimitedCredit feature is enabled, the new limit for a subsequent LimitedCredit API is set to the sum of Debit API within one transaction before issuing a CommitTransaction API. This assures that a LimitedCredit API can not re-book more values than a debiting transaction deducted before. This API requires a preceding authentication with the key specified for 'Write' or 'Read & Write ' Access Rights.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The value file number to be decreased.
	 * @param CmtSet Communication Settings as defined in API 'MF_DF_GetFileSettingsEx()'. The 
	parameter must be same as the communication settings linked to the file.
	 * @param DecValue The parameter holds the value which will be subtracted from the current value stored 
	in the file. Only positive values are allowed for this API.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_Debit(int DeviceAddress,byte CID, byte FileID, byte CmtSet,int DecValue);
	/**
	 * The API allows to write data to a record in a Cyclic or Linear Record File. This API appends one record at the end of the linear record file, it erases and overwrites the oldest record in case of a cyclic record file If it is already full. The entire new record is cleared before data is written to it. If no CommitTransaction API is sent after a WriteRecord API, the next WriteRecord API to the same file writes to the already created record. After sending a CommitTransactionEx() API, a new WriteRecord API will create a new record in the record file. An AbortTransactionEx() API will invalidate all changes. After issuing a ClearRecordFile API, but before a CommitTransactionEx()/AbortTransactionEx() API a WriteRecord API to the same record file will fail. Depending on the communication settings linked to the file, data needs to be sent by the reader either plain, MaCed or full enciphered. This API requires a preceding authentication either with the key specified for 'Write' or 'Read & Write' Access Rights.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The record file number to be written.
	 * @param CmtSet Communication Settings as defined in API 'MF_DF_GetFileSettingsEx()'. The 
	parameter must be same as the communication settings linked to the file.
	 * @param Offset The parameter codes the offset within one single record (in bytes).This parameter has 
	to be in the range from 0 to record size -1.
	 * @param RecordLength The parameter codes the length of data which is to be written to the record file. This 
	parameter has to be in the range from 1 to record size -offset.
	 * @param RecordData The buffer of data to be written to the record file.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_WriteRecord(int DeviceAddress,byte CID,byte FileID,byte CmtSet, int Offset,int RecordLength,byte[] RecordData);
	/**
	 * The API allows to read out a set of complete records from a Cyclic or Linear Record 
	File. In Cyclic Record Files the maximum number of stored valid records is one less than the number of records specified in the CreateCyclicRecordFileEx() API. A ReadRecords API on an empty record file will result in an error. Depending on the communication settings linked to the file, data will be sent by the reader either plain, MACed or full enciphered. This API requires a preceding authentication either with the key specified for 'Read' or 'Read & Write' Access Rights.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The file number to be read from.
	 * @param CmtSet Communication Settings as defined in API 'MF_DF_GetFileSettingsEx()'. The 
	parameter must be same as the communication settings linked to the file.
	 * @param Offset The parameter codes the offset of the newest record which is read out. In case of 0 the 
	latest record is read out. The offset value must be in the range from 0 to number of existing records-1.
	 * @param ReadNumber The parameter codes the number of records to be read from the card. Records are 
	always transmitted by the card in chronological order (= starting with the oldest, which 
	is number of records-1 before the one addressed by the given offset). If this parameter 
	is set to 0 then all records, from the oldest record up to and including the newest 
	record(given by the offset parameter) are read. The allowed range for the number of 
	records parameter is from 0 to number of existing records-offset.
	 * @param RecSize The parameter is the size of one single record, it must be same as the file. 
	 * @param ReturnData The buffer to save the data read from the card.
	 * @param ReturnDataLen The length of data in bytes read from the card if the API successfully.
	 * @return 0 - Successful.
	 */	
	public native static int  MF_DFEV1_ReadRecord(int DeviceAddress,byte CID,byte FileID,byte CmtSet, int Offset,int ReadNumber,int RecSize,byte[] ReturnData,int[] ReturnDataLen);
	/**
	 * The API allows to reset a Cyclic or Linear record File to the empty state. After 
	executing this API but before CommitTransactionEx(), all subsequent WriteRecord API will fail. The readRecords API will return the old still valid records. After the CommitTransactionEx() API is issued, a ReadRecords API will fail, WriteRecord API will be successful. An AbortTransactionEx() API (instead of CommitTransactionEx()) will invalidate the clearance. Full 'Read &Write' permission on the file is necessary for executing this API.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param FileID The file number to be clear.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_ClearRecordFile(int DeviceAddress,byte CID,byte FileID);
	/**
	 * The API allows to validate all previous write access on Backup Data Files, Value Files 
	and Record Files within one application. This API validates all write access to files with integrated backup mechanisms: Backup Data Files, Value Files, Linear Record Files, Cyclic Record Files. This API is typically the last API of a transaction before the ISO 14443-4 Deselect API or before proceeding with another application (SelectApplicationEx() API). As logical counter-part of the CommitTransactionEx() API the AbortTransactionEx() API allows to invalidate changes on files with integrated backup management.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID  The logical number of the addressed, you can active multiple cards simultaneously    
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_CommitTransaction(int DeviceAddress,byte CID);
	/**
	 * The API allows to invalidate all previous write access on Backup Data Files, Value Files  and Record Files within on application. This is useful to cancel a cancel a transaction without the without the need for re-authentication to the card which would lead to the same functionality. This API invalidates all write access to files with integrated backup mechanisms without changing the authentication status: Backup Data Files, Value Files, Linear Record Files, Cyclic Record Files.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1_AbortTransaction(int DeviceAddress,byte CID); 
	/**
	 * Save the key to reader for high level commands.
	 * @param DeviceAddress Device Address of the reader.
	 * @param KeyNo the Key No. to be saved, the value have the range for 0 to 13.
	 * @param CryptoType The crypto type to authenticate,0-DES/3DES, 1-3Key 3DES, 2-AES.
	 * @param Key the key, it is 16 bytes length for DES/3DES and AES Key, but send data is 24 bytes, So 
	should padding 8 bytes(0x00), and 24 bytes length for 3Key 3DES Key.
	 * @return 0 - Successful.
	 */
	public native static int MF_DFEV1_KeySetting(int DeviceAddress,byte KeyNo,byte CryptoType,byte[] Key);
	/**
	 * save the file setting to reader for wiegant mode (auto-read).
	 * @param DeviceAddress Device Address of the reader.
	 * @param AID The application to be selected.
	 * @param KeyNo the Key use to authenticate, it is saved in reader by command the API MF_DFEV1_KeySetting, also, the crypto type saved too.
	 * @param FileID the file to be read.
	 * @param FileType the file type to be read 0-standard data file, 1-backup data file, 2-value file, 3-liner 
	record file, 4-cycle record file.
	 * @param CmtSet Communication Settings as defined in API 'GetFileSettings()'. The parameter must be 
	same as the communication settings linked to the file.
	 * @param Offset the Offset of file to be read.
	 * @param Length the Length of file to be read.
	 * @param RecSize the record size of one record, it is needed for record file, for other file it should be zero.
	 * @return 0 - Successful.
	 */
	public native static int MF_DFEV1_FileSetting(int DeviceAddress,int AID,byte KeyNo,byte FileID,byte FileType, byte CmtSet,int Offset,int Length,int RecSize);  
	
	/**
	 * The API is high level API to read standard, backup and record file, it have Integrated 
	with multiple APIs RATS, PPS and authentication.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    (selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param AID The application to be selected.
	 * @param KeyNo the Key use to authenticate, it is saved in reader by command the API MF_DFEV1_KeySetting, also, the crypto type is saved too.
	 * @param FileID the file to be read.
	 * @param FileType the file type to be read 0-standard data file, 1-backup data file, 3-liner record file, 
	4-cycle record file.
	 * @param CmtSet Communication Settings as defined in API 'GetFileSettings()'. The parameter must be 
	same as the communication settings linked to the file.
	 * @param Offset the Offset of file to be read.
	 * @param Length the Length of file to be read.
	 * @param RecSize the record size of one record, it is needed for record file, for other file it should be zero.
	 * @param Data the buffer to received the data read.
	 * @param ReadLen the length of data have read.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1HL_Read(int DeviceAddress,byte CID,int AID,byte KeyNo, byte FileID,byte FileType,byte CmtSet,int Offset,int Length, int RecSize,byte[] Data,int[] ReadLen);
	/**
	 * The API is high level API to write standard, backup and record file, it have Integrated 
	with multiple APIs RATS, PPS and authentication.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param AID The application to be selected.
	 * @param KeyNo the Key use to authenticate, it is saved in reader by command the API MF_DFEV1_KeySetting, also, the crypto type is saved too.
	 * @param FileID the file to be read.
	 * @param FileType the file type to be read 0-standard data file, 1-backup data file, 3-liner record file, 
	4-cycle record file.
	 * @param CmtSet Communication Settings as defined in API 'GetFileSettings()'. The parameter must be 
	same as the communication settings linked to the file.
	 * @param Offset the Offset of file to be read.
	 * @param Length the Length of file to be read.
	 * @param Data the data to write.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1HL_Write(int DeviceAddress,byte CID,int AID,byte KeyNo, byte FileID,byte FileType,byte CmtSet,int Offset,int Length, byte[] Data);
	/**
	 * The API is high level API to clear record file, it have Integrated with multiple APIs 
	RATS, PPS and authentication.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param AID The application to be selected.
	 * @param KeyNo the Key use to authenticate, it is saved in reader by command the API 
	MF_DFEV1_KeySetting, also, the crypto type is saved too.
	 * @param FileID the file to be clear.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1HL_ClearReads(int DeviceAddress,byte CID,int AID,byte KeyNo, byte FileID);
	
	/**
	 * The API is high level API to read the value file, it have Integrated with multiple APIs 
	RATS, PPS and authentication.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    (selected   by a    single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param AID The application to be selected.
	 * @param KeyNo the Key use to authenticate, it is saved in reader by command the API MF_DFEV1_KeySetting, also, the crypto type is saved too.
	 * @param FileID the file to be clear.
	 * @param CmtSet Communication Settings as defined in API 'GetFileSettings()'. The parameter must be same as the communication settings linked to the file.
	 * @param RetValue the value returned.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1HL_GetValue(int DeviceAddress,byte CID,int AID,byte KeyNo, byte FileID,byte CmtSet, int[] RetValue);

	/**
	 * The API is high level command to credit the value of file with a value, it have Integrated 
	multiple APIs RATS, PPS and authentication.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    
	(selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param AID The application to be selected.
	 * @param KeyNo the Key use to authenticate, it is saved in reader by command the API 
	MF_DFEV1_KeySetting, also, the crypto type is saved too.
	 * @param FileID the file to be clear.
	 * @param CmtSet Communication Settings as defined in API 'GetFileSettings()'. The parameter must be same as the communication settings linked to the file.
	 * @param IncValue the value to credit.
	 * @param IsLimited If Limited enable.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1HL_Credit(int DeviceAddress,byte CID,int AID,byte KeyNo, byte FileID,byte CmtSet, int IncValue,boolean IsLimited); // false
	
	/**
	 * The API is high level command to debit the value of file with a value, it have Integrated multiple APIs RATS, PPS and authentication.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously    (selected by a single reader), this CID is assigned by MF_DF_RATS() or MF_DF_RATSEx(), the CID number must be in the range 0-14.
	 * @param AID The application to be selected.
	 * @param KeyNo the Key use to authenticate, it is saved in reader by command the API MF_DFEV1_KeySetting, also, the crypto type is saved too.
	 * @param FileID the file to be clear.
	 * @param CmtSet Communication Settings as defined in API 'GetFileSettings()'. The parameter must be same as the communication settings linked to the file.
	 * @param DecValue a value to be debited.
	 * @return 0 - Successful.
	 */
	public native static int  MF_DFEV1HL_Debit(int DeviceAddress,byte CID, int AID,byte KeyNo, byte FileID,byte CmtSet, int DecValue);

	/**
	 * This is API of security level 0, it is used to change the data and AES keys from the 
	initial delivery configuration to a customer specific value. 
	  Note: 
	             		1). Before the plus card is switched to higher security levels, the data or key of BNr must be changed: 9000, 9001, 9002, 9003, 9004, this is mandatory, and we recommend to write all other keys and configuration blocks too
	                		2). Plus S card does not support SL2, therefore the BNr 9004 of block is unnecessary for plus S card.
	                  	3). For sector trailer, you must know the detail of access conditions, or don't write it, if 
				the data is incorrect, the sector will not be accessed any more, the BNr of sector trailer is such as the number: 03, 07, 0B, 0F...... act.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously 
	(selected by a single reader), this CID is assigned by API RATS(), the CID number must be in the range 0-14.
	 * @param BNr Indicate the key and block number of plus card, the following values are in hex format.
	9000: Card Master Key
	9001: Card Configuration Key
	9002: Level 2 Switch Key
	9003: Level 3 Switch Key
	9004: SL1 Card Authentication Key
	B000: MFP Configuration Block
	B001: Installation Identifier
	B002: ATS Information
	B003: Field Configuration Block
	0000-00FF: Mifare Blocks (Sector 0-39)
	4000-404F: AES Sector Keys for sector 0-39, the second byte defines the sector number and which key (Key A or Key B) is used. Key A=Sector number multiplied by 2, Key B=sector number multiplied by 2+1, E.g. Key A  for sector 2 has the number :4004.
	 * @param Data the data or AES key to be written to plus card, it is 16 bytes.
	 * @return 0 - Successful.
	 */
	public native static int MF_Plus_WritePerso(int DeviceAddress,byte CID,short BNr,byte[] Data);
	
	/**
	 * This is API of security level 0, it is used to finalize the personalization and
	 * switch up to security level 1 for an "L1 card" or security level 3 for an "L3 card".
	 * @param DeviceAddress  Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously
	 * @return 0 - Successful.
	 */
	public native static int MF_Plus_CommitPerso(int DeviceAddress,byte CID);
	
	/**
	 * This is API of security level 1, it is used to authenticate a plus card,it is optional.
	 * Security level1 offers the same features as Mifare classic(MF1 ICS50 or MF1 ICS70).
	 * @param DeviceAddress  Device Address of the reader.
	 * @param AESKey 16bytes AES key, the AESKey(BNr is 9004) must be written at security level 0 via API
	 *               "MF_Plus_WritePerso()", and you must remember it, for it can't be changed at level 1
	 *               any more.
	 * @return 0 - Successful.
	 */
	public native static int MF_Plus_SL1AESAuth(int DeviceAddress,byte[] AESKey);
	
	/**
	 * This is API of security level 1, it is used to authenticate a plus card,it is optional.
	 * Security level1 offers the same features as Mifare classic(MF1 ICS50 or MF1 ICS70).
	 * @param DeviceAddress  Device Address of the reader.
	 * @param BNr Indicate the key and block number of plus card, more information, please refer to API 
				  'MF_Plus _WritePerso()'.
	 * @param AESKey 16bytes AES key, the AESKey must be written at security level 0 via API
	 *               "MF_Plus_WritePerso()", and you must remember it, for it can't be changed at level 2
	 *               any more.
	 * @param MFKey 6 bytes Mifare key of sector to be authenticated
	 * @param MFSessionKey if the API call successfully, it will receive a Mifare session key(6 bytes),you 
	 *                can use the Mifare session key to authenticate the card, and then read/write the card,
	 *                every calling the API, the Mifare session key are different.
	 * @return 0 - Successful.
	 */
	public native static int MF_Plus_SL2AESAuth(int DeviceAddress,short BNr,byte[] AESKey,byte[] MFKey,byte[] MFSessionKey);
	
	/**
	 * The API can be used at security level 1, 2 and 3, it is used to authenticate a plus card. 
	                  For security level 1, it is used to switch to higher security level 2(BNr 9002) or 3 (BNr 
				9003)
	                  	For security level 2, it is used to switch to higher security level 3 (BNr 9003)
	                    	For security level 3, it is used to authenticate a sector of plus card
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously 
	(selected by a single reader), this CID is assigned by API RATS(), the CID number must be in the range 0-14.
	 * @param BNr Indicate the key and block number of plus card, more information, please refer to API 
	'MF_Plus _WritePerso()'.
	 * @param AESKey 16 bytes AES key.
	 * @return 0 - Successful.
	 */
	public native static int MF_Plus_FirstAuth(int DeviceAddress,byte CID,short BNr,byte[] AESKey);
	
	/**
	 * The API is used at security level 3, it is used to authenticate a plus card, before calling the API, it is necessary to firstly call 'MF_Plus_FirstAuth' successfully.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously 
	(selected by a single reader), this CID is assigned by API RATS(), the CID number must be in the range 0-14.
	 * @param IsPlusX Indicate the type of the current plus card, false-plus S, true-plus X.
	 * @param BNr Indicate the key and block number of plus card, more information, please refer to API 'MF_ Plus_WritePerso()'.
	 * @param AESKey 16 bytes AES key.
	 * @return 0 - Successful.
	 */
	public native static int MF_Plus_FollowAuth(int DeviceAddress,byte CID,boolean IsPlusX,short BNr,byte[] AESKey);

	/**
	 * The API is used at security level 3,it is used to reset the authentication,after it be
	 * calling successfully,you need to call "MF_Plus_FirstAuth" again for reading/wrting.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, value is 1.
	 */
	public native static int MF_Plus_SL3ResetAuth(int DeviceAddress,byte CID);
	
	/**
	 * The API is used at security level 3, it is used to read data from card, before the API, authentication is necessary.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously (selected by a single reader), this CID is assigned by API RATS(), the CID number must be in the range 0-14.
	 * @param IsPlusX Indicate the type of the current plus card, false-plus S, true-plus X.
	 * @param BNr Indicate the key and block number of plus card, more information, please refer to API 'MF_ Plus _WritePerso()'.
	 * @param BlockNum Indicate how many blocks to read out within one sector, it must be in the rang 1-3.
	 * @param MACC Indicate if a MAC code enable or not when send the read command to card, false-disenable,  true-enable. For plus s card, it must be true.
	 * @param Plaintext Indicate if data read in plain or not when receive data from card, false-disenable, true-enable. For plus s card, it must be true.
	 * @param MACR Indicate if a MAC code enable or not when receive data from card, false-disenable, true-enable. For plus s card, it must be true.
	 * @param DataRead buffer used to receive data read from card.
	 * @param DataReadLen data length (bytes) read from card.
	 * @return 0 - Successful
	 */
	public native static int MF_Plus_SL3Read(int DeviceAddress,byte CID,boolean IsPlusX,short BNr,byte BlockNum,boolean MACC,boolean Plaintext,boolean MACR,byte[] DataRead,byte[] DataReadLen);
	/**
	 * The API is used at security level 3, it is used to write data to card, before the API, authentication is necessary.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously (selected by a single reader), this CID is assigned by API RATS(), the CID number must be in the range 0-14.
	 * @param IsPlusX Indicate the type of the current plus card, false-plus S, true-plus X.
	 * @param BNr Indicate the key and block number of plus card, more information, please refer to API 'MF_ Plus _WritePerso()'.
	 * @param Plaintext Indicate if data in plain or not when write to card, false-disenable, true-enable. For plus s card, it must be true.
	 * @param MACR Indicate if a MAC code enable or not when receive data from card, false-disenable, true-enable. For plus s card, it must be true.
	 * @param DataToWrite  buffer contains the data to be written to card.
	 * @return 0 - Successful.
	 */
	public native static int MF_Plus_SL3Write(int DeviceAddress,byte CID,boolean IsPlusX,short BNr, boolean Plaintext,boolean MACR,byte[] DataToWrite);
	/**
	 * The API is used at security level 3, it is used to operating a value saved in a block of card, before the API, authentication is necessary.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The logical number of the addressed, you can active multiple cards simultaneously (selected by a single reader), this CID is assigned by API RATS(), the CID number must be in the range 0-14.
	 * @param OPType The operation on value saved in block of card. 0-Initialize value block, 1-Read Value, 2-Increment, 3-Decrement, 4-Transfer,5-Restore, 6-combination of Increment and Transfer, 7-combination of Decrement and Transfer.
	 * @param BNr Indicate the key and block number of plus card, more information, please refer to API 'MF_Plus_WritePerso()'.
	 * @param MACR Indicate if a MAC code enable or not when receive data from card, false-disenable, true-enable. 
	 * @param Value The value to be operated in block of card, if OPType is 1 (reading operation), parameter Value is the output result read from card, if OPType is 4-Transfer or 5-Restore, Value is unnecessary just pass it a NULL, for other operation : 0-Initialize, 2-Increment, 3-Decrement, 6-combination of Increment and Transfer, 7-combination of Decrement and Transfer, the Value is the input parameter, you should pass a value.
	 * @return 0 - Successful.
	 */
	public native static int MF_Plus_SL3Value(int DeviceAddress,byte CID,byte OPType,short BNr,boolean MACR,int[] Value);
	/**
	 * The API is used to configure the reader for Mifare Plus card auto-read.
	 * @param DeviceAddress Device Address of the reader.
	 * @param Setting Setting[0]		bit2 - Card Type(Plus S or Plus X)
						bit3 - MAC on command
						bit4 - Plain Text
						bit5 - MAC on response
	Setting[1]		SL3 follow authentication
	Setting[2]		The block number for reading
	Setting[3]		The length for output
	Setting[4...19]	AES key(16 bytes)
	 * @return 0 - Successful.
	 */
	public native static int MF_Plus_AutoReadSetting(int DeviceAddress,byte[] Setting);
	/**
	 * The API is used to get auto-read setting from the reader for Mifare Plus card.
	 * @param DeviceAddress Device Address of the reader
	 * @param Setting Pointer to store Auto-Read setting returned from reader.
	 * @return 0 - Successful.
	 */
	public native static int MF_Plus_GetAutoReadSetting(int DeviceAddress,byte[] Setting);
	
	public String UID = "";
	
	/**
	 * A callback function. Once reader have read data, data will be sent to PC.
	 * @param sUID Receive UID.
	 */
	public void TOnAutoRead(String sUID) {
		this.UID = sUID;
	}
	
	/**
	 * Set a callback function, it will be called automatically once reader have read card after you call API AutoRead_Run(), it let you know when and what data have read.
	 * @return 0 - Successful.
	 */
	public native int  SetOnAutoRead();
	
	/**
	 * Set SDK to automatically monitor data, in this state, the callback function which set by SetOnAutoRead() or SetOnAutoReadEx() will be called automatically once reader have read card after you call API AutoRead_Run(), it let you know when and what data have read. Note: if you want to sent other command to reader, you should firstly stop the state by API AutoRead_Stop()
	 * @return 0x00 - Successful (Refer the API return code for other values)
	 */
	public native static int  AutoRead_Run();
	
	/**
	 * The API is used to Select auto read card type.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CardType the auto read card type: 0--Mifare   1--MifarePlus    2--Mifare DESFire
	 * @param blockNum the block num of the mifare, for Plus and desfire card the blockNum is 0.
	 * @return 0 - Successful.
	 */
	public native static int AutoRead_SelectCard(int DeviceAddress, byte CardType, byte blockNum);
	
	/**
	 * Stop the automatically monitor data state, if you want to sent other command to reader, you should firstly stop the auto read mode
	 * @return 0x00 - Successful (Refer the API return code for other values)
	 */
	public native static int  AutoRead_Stop();
	
	/**
	 * Read if SDK is in the automatically monitor data state.
	 * @return 0x00 - SDK do not in auto-read mode.
               0x01 - SDK is in auto-read mode.
	 */
	public native static int  IsAutoRead();

	/**
	 * PN532 generic command.
	 * @param DeviceAddress Device Address of the reader.
	 * @param length The length of buffer.
	 * @param buffer APDU to/form card.
	 * @return 0 - Successful.
	 */
	public native static int SLE_Generic_PN532(int DeviceAddress,byte[] length,byte[] buffer);
	
	/**
	 * The API is used to detect as many targets (maximum MaxTg) as possible in passive mode.
	 * @param DeviceAddress Device Address of the reader.
	 * @param CardType You will get 0 for all kinds of card types, but 1 is purposed for Mifare Plus SL0.
	 * @param MaxTg Up to 2 cards can be found at the same time. But, you can set 1 to read just one card.
	 * @param BaudRate Communication baud rate to be used to find card,
		0. 	106 kpbs, ISO14443 Type A.
		1.	212 kbps, FeliCa Polling
		2.	424 kbps, FeliCa Polling
		3.	106 kbps ISO14443-3B
		4.	106 kbps Innovision Jewel tag
	 * @param IniDataLen The length(bytes) of the next parameter 'IniData'.
	 * @param IniData Depending on the baud rate specified, the content of this field is different:
	         		--106 kbps typeA
	The field is optional and is present only when the host controller wants to initialize a card with a known UID.
	in that case, IniData contains the UID of the card(or part of it). The UID must include the cascade tag CT if it is cascaded level2 or 3
	Cascade levle1: UID1, UID2, UID3, UID4
	Cascade levle2: CT, UID1, UID2, UID3, UID4, UID5, UID6, UID7
	Cascade levle3: CT, UID1, UID2, UID3, UID4, UID5, UID6, UID7, UID8, UID9, UID10
	--106 kbps type B
	In this case, IniData is formatted as following
	AFI(1byte), Polling Method(option)
	AFI: Application Family Identifier, parameter represents the type of application targeted by PN532 chip in reader and is used to pre-select the cards before the ATQB, AFI is mandatory
	Polling Method: this is optional. It indicates the approach to be used in the ISO14443-3B initialization
	Bit0=1, Probabilistic approach(option 1) in the ISO14443-3B initialization
	Bit0=0, Timeslot approach(option 2) in the ISO14443-3B initialization
	if the field is absent, the timeslot approach will be used.
	--212/424 kbps:
	In that case, this field is mandatory and contains the complete payload information that should be used in the polling request command(5 bytes, length byte is excluded) as defined in NFC protocol.
	--106 kbps Innovision Jewel tag:
	This field is not used
	 * @param NbTg out parameter, indicating the number of cards found.
	 * @param RDataLen out parameter, indicating the length (bytes) of next parameter RData.
	 * @param RData out parameter, cards information got from reader. Contains the information about the detected targets and depends on the baud rate selected. The following information is given for one target, it is repeated for each target initialized (NbTg times).
	--106 kbps Type A
	-----------------------------------------------------------------------------------------------------------------------------------------------------
	-	Tg			-		SENS_RES		-		SEL_RES		-		NFCID1Length		-		NFCID1					-		ATS(option)			-
	-----------------------------------------------------------------------------------------------------------------------------------------------------
	-	(1 byte)	-		(2 bytes)		-		(1 byte)	-		(1 byte)			-		(NFCID1Length bytes)	-		(ATSLength bytes)	-
	----------------------------------------------------------------------------------------------------------------------------------------------------- 	       
	
	--106 kbps Type B
	-------------------------------------------------------------------------------------------------------------
	-	Tg			-		ATQB Response		-		ATTRIB_RES Length		-		ATTRIB_RES(option)		-
	-------------------------------------------------------------------------------------------------------------
	-	(1 byte)	-		(12 bytes)			-		(1 byte)				-		ATTRIB_RES Length bytes	-
	-------------------------------------------------------------------------------------------------------------
	
	--212/424 kbps
	-------------------------------------------------------------------------------------------------------------------------------------------------
	-	Tg			-		POL_RES Length		-		0x01(Response code)		-		NFCID2t		-		Pad			-		SYST_CODE(option)	-
	-------------------------------------------------------------------------------------------------------------------------------------------------
	-	(1 byte)	-		(1 byte)			-		(1 byte)				-		(8 bytes)	-		(8 bytes)	-		(2 bytes) 			-
	-------------------------------------------------------------------------------------------------------------------------------------------------
    
	--106 kbps Innovision Jewel tag:
	-------------------------------------------------------------
	-	Tg			-		SENS_RES		-		JEWELID		-
	-------------------------------------------------------------
	-	(1 byte)	-		(2 bytes)		-		(4 bytes)	-	
	-------------------------------------------------------------
		
	 * @return 0 - Successful.
	 */
	public native static int PN_InListPassiveTarget(int DeviceAddress,byte CardType,byte MaxTg,byte BaudRate,byte IniDataLen, byte[] IniData,byte[] NbTg,byte[] RDataLen,byte[] RData);
	/**
	 * The API is used to select target to subsequent operation.
	 * @param DeviceAddress Device Address of the reader.
	 * @param TargetNum The logical number of target to be selected.
	 * @return 0 - Successful.
	 */
	public native static int PN_SelectTarget(int DeviceAddress,byte TargetNum);
	/**
	 * The API is used to exchange data between two readers (Peer To Peer), one works as Peer sender and the other works as Peer receiver. Please be noted that both readers must call the API at the same time.
	 * @param DeviceAddress Device Address of the reader.
	 * @param InTg works as Peer sender or Peer receiver, 0x00-Peer sender, 0x01-Peer receiver.
	 * @param BaudRate Communication baud rate between two peers during exchanging data, 0-106 kbps, 1-212 kbps, 2-424 kbps.
	 * @param TimeOut The maximum time for executing this function. For COM operation, the real timeout is TimeOut*10+110.
	 * @param NFCID3 You can define a random ID(10 bytes) to identify reader during exchanging data, it will be get at the other Peer, this is an In/Out parameter.
	 * @param Data Data to be exchanged, this is an In/Out parameter, In means data to be sent, Out means the data received from the other Peer.
	 * @param DataLen Indicate the parameter 'Data's length (bytes), this is an in/out parameter.
	 * @return 0 - Successful.
	 */
	public native static int PN_ExchangeData(int DeviceAddress,byte InTg,byte BaudRate,byte TimeOut, byte[] NFCID3,byte[] Data,byte[] DataLen);
	/**
	 * This API is used to switch on/off SAM card, it's needed for subsequent operations.
	 * @param DeviceAddress Device Address of the reader.
	 * @param ucCardSlot card slot value is 1,2,3,4(please only use 1 for present status).
	 * @param ATR ATR String.
	 * @param nATR_Len The ATR length of send to and recive from reader.
	 * @param nATR_MaxLen ATR maxlength of send to and recive from reader.
		CNReaderType
						Default value is 1.
						Two value:
						1	CN370 or CN670, The default value.
						2	CN313 or CN613
	 * @return 0 - Successful.
	 */
	public native static int ISO7816_ATR( int DeviceAddress, byte ucCardSlot, byte[] ATR, int[] nATR_Len, int nATR_MaxLen);
	/**
	 * This API is used to exchange data(APDU) between host controller and SAM card, all the data to be sent and received from card, you can refer to ISO/IEC 7816.
	 * @param DeviceAddress Device Address of the reader.
	 * @param ucCardSlot card slot value is 1,2,3,4(Please only use 1 for present status).
	 * @param DataToCard Data of send to card.
	 * @param nbytes_DataToCard the length of DataToCard.
	 * @param DataFromCard Data of receive from card.
	 * @param nbytes_DataFromCard The length of DataFromCard.
	 * @param nMaxbytes_DataFromCard The max length allowed of DataFromCard.
		CNReaderType
						Default value is 1.
						Two value:
						1	CN370 or CN670, The default value.
						2	CN313 or CN613
	 * @return 0 - Successful.
	 */
	public native static int ISO7816_APDU_Exchange(int DeviceAddress, byte ucCardSlot, byte[] DataToCard, int nbytes_DataToCard, byte[] DataFromCard, int[] nbytes_DataFromCard, int nMaxbytes_DataFromCard);
	/**
	 * The API is used to send by an LLC whenever no other PDUs are available for sending, 					to ensure symmetry.
	   Note: Other parameters and RetData , please refer to 'Description for Parameter and RetData'.
	 * @param DeviceAddress Device Address of the reader.
	 * @param RetData
	 * @return 0 - Successful.
	 */
	public native static int NFC_LLCP_SYMM(int DeviceAddress, byte[] RetData);
	/**
	 * The API is an unnumbered PDU which is used to request a data link connection between a source and a destination service access point.
		Note: Other parameters and RetData , please refer to 'Description for Parameter and RetData'.
	 * @param DeviceAddress Device Address of the reader.
	 * @param DSAP
	 * @param SSAP
	 * @param MIUX
	 * @param RW
	 * @param SN
	 * @param RetData
	 * @return 0 - Successful.
	 */
	public native static int NFC_LLCP_CONNECT(int DeviceAddress, byte DSAP, byte SSAP, byte[] MIUX, byte RW, byte[] SN, byte[] RetData);
	/**
	 * The API is an unnumbered PDU which is used by an LLC to acknowledge the receipt and acceptance of the Connect PDU.
	   Note: Other parameters and RetData , please refer to 'Description for Parameter and RetData'.
	 * @param DeviceAddress Device Address of the reader
	 * @param DSAP
	 * @param SSAP
	 * @param MIUX
	 * @param RW
	 * @param RetData
	 * @return 0 - Successful.
	 */ 
	public native static int NFC_LLCP_CC(int DeviceAddress, byte DSAP, byte SSAP, byte[] MIUX, byte RW, byte[] RetData);
	/**
	 * The API is an unnumbered PDU which is used to terminate a data link connection or is used to deactivate the LLCP link.
	   Note: Other parameters and RetData , please refer to 'Description for Parameter and RetData'.
	 * @param DeviceAddress DeviceAddress of the reader.
	 * @param DSAP
	 * @param SSAP
	 * @param RetData
	 * @return 0 - Successful.
	 */
	public native static int NFC_LLCP_DISC(int DeviceAddress, byte DSAP, byte SSAP, byte[] RetData);
	/**
	 * The API is a numbered PDU which is used to transfer service data units across a data link connection.
	   Note: Other parameters and RetData , please refer to 'Description for Parameter and RetData'.
	 * @param DeviceAddress Device Address of the reader.
	 * @param DSAP
	 * @param SSAP
	 * @param SEQ
	 * @param Data
	 * @param DataLen
	 * @param RetData
	 * @return 0 - Successful.
	 */
	public native static int NFC_LLCP_I(int DeviceAddress, byte DSAP, byte SSAP, byte SEQ, byte[] Data, int DataLen, byte[] RetData);
	/**
	 * The API is a numbered PDU which is used by an LLC to acknowledge one or more received I PDUs and indicate the LLC is able to receive subsequent I PDUs.
	   Note: Other parameters and RetData , please refer to 'Description for Parameter and RetData'.
	 * @param DeviceAddress Device Address of the reader.
	 * @param DSAP
	 * @param SSAP
	 * @param SEQ
	 * @param RetData
	 * @return 0 - Successful.
	 */
	public native static int NFC_LLCP_RR(int DeviceAddress, byte DSAP, byte SSAP, byte SEQ, byte[] RetData);
	/**
	 * The API is a numbered PDU which is used by an LLC to indicate a temporary inability to process subsequent I PDUs.
	   Note: Other parameters and RetData , please refer to 'Description for Parameter and RetData'.
	 * @param DeviceAddress Device Address of the reader.
	 * @param DSAP
	 * @param SSAP
	 * @param SEQ
	 * @param RetData
	 * @return 0 - Successful.
	 */
	public native static int NFC_LLCP_RNR(int DeviceAddress, byte DSAP, byte SSAP, byte SEQ, byte[] RetData);
	/**
	 * The API is an unnumbered PDU which is used to report status indicating that the LLC is logically disconnected from the data link connection identified by the DSAP and SSAP address pair.
	   Note: Other parameters and RetData , please refer to 'Description for Parameter and RetData'.
	 * @param DeviceAddress Device Address of the reader.
	 * @param DSAP
	 * @param SSAP
	 * @param RESON
	 * @param RetData
	 * @return 0 - Successful.
	 */
	public native static int NFC_LLCP_DM(int DeviceAddress, byte DSAP, byte SSAP, byte RESON, byte[] RetData);
	/**
	 * The API is an unnumbered PDU which is used to report the receipt of a malformed or inappropriate PDU on a data link connection.
	   Note: Other parameters and RetData , please refer to 'Description for Parameter and RetData'.
	 * @param DeviceAddress Device Address of the reader
	 * @param DSAP
	 * @param SSAP
	 * @param INFO
	 * @param RetData 
	 * @return 0 - Successful.
	 */
	public native static int NFC_LLCP_FRMR(int DeviceAddress, byte DSAP, byte SSAP, byte[] INFO, byte[] RetData);
	/**
	 * The API is used to transfer service data units to the peer LLC without prior establishment of a data link connect.
	   Note: Other parameters and RetData , please refer to 'Description for Parameter and RetData'.
	 * @param DeviceAddress Device Address of the reader.
	 * @param DSAP
	 * @param SSAP
	 * @param Data
	 * @param RetData
	 * @return 0 - Successful.
	 */
	public native static int NFC_LLCP_UI(int DeviceAddress, byte DSAP, byte SSAP, byte[] Data, byte[] RetData);
	/**
	 * The API is an unnumbered PDU used by a local LLC to discover the availability of named services in the remote service environment.
	   Note: Other parameters and RetData , please refer to 'Description for Parameter and RetData'.
	 * @param DeviceAddress Device Address of the reader.
	 * @param DSAP 
	 * @param SSAP
	 * @param TID
	 * @param SN_URI
	 * @param RetData 
	 * @return 0 - Successful.
	 */
	public native static int NFC_LLCP_SDREQ(int DeviceAddress, byte DSAP, byte SSAP, byte TID, byte[] SN_URI, byte[] RetData);
	/**
	 * The API is an unnumbered PDU used to respond to discovery requests from the remote LLC.
	   Note: Other parameters and RetData , please refer to 'Description for Parameter and RetData'.
	 * @param DeviceAddress Device Address of the reader
	 * @param DSAP
	 * @param SSAP
	 * @param TID
	 * @param SAP
	 * @param RetData
	 * @return 0 - Successful.
	 */
	public native static int NFC_LLCP_SDRES(int DeviceAddress, byte DSAP, byte SSAP, byte TID, byte SAP, byte[] RetData);
	
	/**
	 * The API is used to get the UID and configuration informations of the remote LLC from the local LLC. It should be executed before any other subsequent operations.
	 * @param DeviceAddress Device Address of the reader
	 * @param RetData The Data part is defined as below:					
			Data part of RetData
		NFCID[10]	VER	WKS[2]	LTO		OPT
	1)	NFCID[10]: The NFC Forum Device identifier of the Initiator for the NFC-DEP Protocol.
	2)	VER: Version Number. It shall be encoded as an 8-bit structure consisting of two 4-bit unsigned integer values representing the major and minor release levels of 'Logical Link Control Protocol Technical Specification'. The most-significant 4 bits shall denote the major release level. The least-significant 4 bits shall denote the minor release level. The structure is defined as below:
	-----------------------------------------------------------------
	-								VER								-
	-----------------------------------------------------------------
	-	b7	-	b6	-	b5	-	b4	-	b3	-	b2	-	b1	-	b0	-
	-----------------------------------------------------------------
	-			(b7-b4)Major		-		(b3-b0)Minor			-
	-----------------------------------------------------------------
	
	3)	WKS[2]: Well-Known Service List. It shall denote the binding of service listeners to well-known service access point addresses and therefore the willingness of the sender of this parameter to accept PDUs on those SAPs(Service Access Point). It's encoded as a 16-bit field. The most-significant bit of the 16-bit field value shall signify SAP address 0Fh and the least-significant bit SHALL signify SAP address 00h. The other bits shall signify SAP addresses corresponding to their respective bit positions. A bit set to '1' shall indicate that a service listener is bound to the corresponding well-known service access point. A bit set to '0' shall indicate that no service listener is bound to the corresponding well-known service access point. The structure is defined as below:
	---------------------------------------------------------------------
	-								WKS[2]							 	-
	---------------------------------------------------------------------
	-	F	E	D	C	B	A	9	8	7	6	5	4	3	2	1	0	-
	---------------------------------------------------------------------
	-	X	X	X	X	X	X	X	X	X	X	X	X	X	X	X	1	-
	---------------------------------------------------------------------
	
	4)	LTO: Link Timeout. It specifies the local link timeout interval guarantee. It shall specify the maximum time interval between the last received bit of an LLC PDU transmission from the remote to the local LLC and the first bit of the subsequent LLC PDU transmission from the local to the remote LLC. It's implementation concern to take into account any internally required processing time and propagation delays. It shall be an 8-bit unsigned integer that specifies the link timeout value in multiples of 10 milliseconds. If no LTO parameter is transmitted of if the LTO parameter value is zero, the default link timeout value of 100 milliseconds shall be used.
	5)	OPT: Option. It communicates the link service class and the set of options supported by the sending LLC. It contains a single 8-bit byte representing a set of flags which indicate the link service class of the sending LLC and the support of optional features implemented by the sending LLC. The format is defined as below:
	-----------------------------------------------------------------
	-				OPT												-
	-----------------------------------------------------------------
	-	b7	-	b6	-	b5	-	b4	-	b3	-	b2	-	b1	-	b0	-
	-----------------------------------------------------------------
	-	0	-	0	-	0	-	0	-	0	-	0	-		LSC		-
	-----------------------------------------------------------------
	
	LSC: Link Service Class. Within the link service class subfield, the two bits, 'b0' and 'b1', indicate support for the connectionless and connection-oriented link transports, respectively:
	1)	Bit LSC b0 indicate support the sending LLC for connectionless transport mode. The sending LLC shall set bit LSC b0 to 1 when connectionless transport is supported. The sending LLC shall set bit LSC b0 to 0 to shen connectionless transport is not supported.
	2)	Bit LSC b1 indicates support in the sending LLC for connection-oriented transport mode. The sending LLC shall set bit LSC b1 to 1 when connection-oriented transport is supported. The sending LLC shall set bit LSC b1 to 0 when connection-oriented transport is not supported. The LSC bits form a 2-bit unsigned value that represents the link service class of the sending device as shown below:
	-----------------------------------------------------------------------------------------------------------------------------
	-	Link Service Class		-	LSC b1		-	LSC b0		-	Definition													-
	-----------------------------------------------------------------------------------------------------------------------------							-				-				-																	-	
	-	unknown					-	0 			-	0			-	Link service class is unknown at time of link activation	-
	-----------------------------------------------------------------------------------------------------------------------------
	-	Class1					-	0			-	1			-	Connectionless link service only							-
	-----------------------------------------------------------------------------------------------------------------------------
	-	Class2					-	1			-	0			-	Connection-oriented link service only						-
	-----------------------------------------------------------------------------------------------------------------------------
	-	Class3					-	1			-	1			-	Both connectionless and connection-oriented link service	-
	-----------------------------------------------------------------------------------------------------------------------------
	 * @return 0 - Successful.
	 */
	public native static int NFC_LLCP_POLL(int DeviceAddress, byte[] RetData);
	
	/**
	 * NFC device have two work mode:Initiator, Target. This API configure the reader as the Target mode. 
	 * @param DeviceAddress Device address of the reader.
	 * @param RetData.
	 * @return 0 - Successful.
	 */
	public native static int NFC_Target(int DeviceAddress, byte[] RetData);
	
	/**
	 * The API is used to read-out the two Header ROM bytes and all of the static memory blocks 0-Eh.
	 * The Header ROM map is as below:
	 * HR0  HR1		
	 * 11h  xxh
	 * 1)	HR0 Upper nibble = 0001b SHALL determine that it is a Type 1, NDEF capable tag.
	 * 2)	HR0 Lower nibble = 0001b SHALL determine static memory map.
	 * 3)	HR0 Lower nibble ≠0001b SHALL determine the dynamic memory map.
	 * 4)	HR1 = xxh is undefined and SHALL be ignored.
	 * The static memory map of the NFC Forum Type 1 Tag is as below(Blk: Block, B: Byte):
	 * 1)	B7 of Blk0 and BlkD is reserved for internal use.
	 * 2)	B0 and B1 of BlkE is User Block Lock & Status.
	 * 3)	B2 to B7 of BlkE is OTP bits
	 * @param DeviceAddress Device address of the reader.
	 * @param uid The data received from polling command.(UID of the card).
	 * @param RetData The Data part contains two Header ROM bytes and all of the static memory blocks 0-Eh. 
	 * The Len part should be calculated like: Status(1 Byte) + Header ROM(2 Bytes) + static memory blocks(15(0-E blocks)*8(each block has 8 bytes data)) = 123(7Bh). .
	 * @return 0 - Successful.
	 */
	
	public native static int NFC_T1T_Rall(int DeviceAddress, byte [] uid, byte [] RetData);	
	
	/**
	 * The API is used to read-out the specified area(add) with the static memory model area of blocks 0-Eh. 
	 * As a pre-condition, this command requires that the tag be in the READY state and afterward, the tag remains in READY state.
	 * @param DeviceAddress Device address of the reader.
	 * @param add A single EEPROM memory byte within the static memory model area of blocks 0-Eh. 
	  * The format is defined as below:	
	 * _______________________________________ 
	 * |Address operand ‘ADD’                |
     * |Block = select one of blocks 0h – Eh |  
     * |Byte = select one of bytes 0 - 7     | 
     * |msb                  lsb             |
     * |b8  b7  b6  b5  b4  b3  b2  b1       |
     * |0b  Static Block       Byte          | 
     * |_____________________________________|
     * The formula is : Block number(0 to E, inclusively) * 8 + Byte offset(0 to 7, inclusively).
	 * For example: 
	 * Block number is 0x00, Byte offset is 0x06, add should be 0x00*8+0x06=0x06.
	 * Block number is 0x04, Byte offset is 0x03, add should be 0x04*8+0x03=0x23.
	 * @param uid The data received from polling command.(UID of the card).
	 * @param RetData The Data part contains the data for the specified area with the static memory model area of blocks 0-Eh. 
	 * @return 0 - Successful.
	 */
		
	public native static int NFC_T1T_Read(int DeviceAddress, byte add, byte [] uid, byte [] RetData); 	
	
	/**
	 * The API is used to erase the target byte for the specified area and then write the new data, 
	 * it relates to an individual memory byte within the static memory model area of blocks 0-Eh. 
	 * If any of Block0 to BlockD is locked, then this command is barred from those blocks. Additionally,
	 *  the command is always barred from Blocks 0, D and E because these are automatically in the locked condition. 
	 *  If the command is barred, the erase-write cycle is skipped-no write operation occurs and the tag will enter READY status waiting for a new command. 
	 *  As a pre-condition, this command requires that the tag be in the READY state and afterward, the tag remains in READY state.
	 * @param DeviceAddress Device address of the reader.
	 * @param add A single EEPROM memory byte within the static memory model area of blocks 0-Eh. 				
	 * The format is defined as below:	
	 * _______________________________________ 
	 * |Address operand ‘ADD’                |
     * |Block = select one of blocks 0h – Eh |  
     * |Byte = select one of bytes 0 - 7     | 
     * |msb                  lsb             |
     * |b8  b7  b6  b5  b4  b3  b2  b1       |
     * |0b  Static Block       Byte          | 
     * |_____________________________________|
     * The formula is : Block number(0 to E, inclusively) * 8 + Byte offset(0 to 7, inclusively).
	 * For example: 
	 * Block number is 0x00, Byte offset is 0x06, add should be 0x00*8+0x06=0x06.
	 * Block number is 0x04, Byte offset is 0x03, add should be 0x04*8+0x03=0x23.
	 * @param uid The data received from polling command.(UID of the card).
	 * @param RetData The Data part contains the data for the specified area with the static memory model area of blocks 0-Eh. 
	 * @return 0 - Successful.
	 */
	
	public native static int NFC_T1T_Write_E(int DeviceAddress, byte add, byte data, byte [] uid, byte [] RetData); 
	
	/**
	 * The API is used to write the data to the specified area with the static memory model area of blocks 0-Eh. 
	 * It relates to an individual memory byte within the static memory model area of blocks 0-Eh.
	 * This command does not erase the target byte before writing the new data, and the execution time is approximately half that of the ‘normal’
	 * write command(NFC_T1T_Write).Bits can be set but not reset(i.e., data bits previously set to a ‘1’ cannot be reset to a ‘0’). If any of Block1 to BlockC are locked, 
	 * the command is barred from that block, but it’s not barred from BlockE to allow setting of lock and OTP bits. If the command is barred , 
	 * the write-no-erase cycle is skipped-no write operation occurs and the tag will return to the “READY” state and wait for a new command. As a pre-condition, 
	 * this command requires that the tag be in the READY state and afterward, the tag remains in READY state. This command has three main purposes:
	 * 1)	Lock – to set the ‘lock bit’ for a block.
	 * 2)	OTP- to set One-Time-Programmable bits(bytes 2 – 7 of BlockE), where between one and eight OTP bits can be set with a single command.
	 * 3)	A fast-write in order to reduce overall time to write data to memory blocks for the first time given that the original condition of memory is zero.
	 * @param DeviceAddress Device address of the reader.
	 * @param add A single EEPROM memory byte within the static memory model area of blocks 0-Eh. 				
	 * The format is defined as below:	
	 * _______________________________________ 
	 * |Address operand ‘ADD’                |
     * |Block = select one of blocks 0h – Eh |  
     * |Byte = select one of bytes 0 - 7     | 
     * |msb                  lsb             |
     * |b8  b7  b6  b5  b4  b3  b2  b1       |
     * |0b  Static Block       Byte          | 
     * |_____________________________________|
     * The formula is : Block number(0 to E, inclusively) * 8 + Byte offset(0 to 7, inclusively).
	 * For example: 
	 * Block number is 0x00, Byte offset is 0x06, add should be 0x00*8+0x06=0x06.
	 * Block number is 0x04, Byte offset is 0x03, add should be 0x04*8+0x03=0x23.
	 * @param uid The data received from polling command.(UID of the card).
	 * @param RetData The Data part contains the data for the specified area with the static memory model area of blocks 0-Eh. 
	 * @return 0 - Successful.
	 */
	
	public native static int NFC_T1T_Write_Ne(int DeviceAddress, byte add, byte data, byte [] uid, byte [] RetData); 
	
	/**
	 * The API is used to read-out a complete segment of memory. A segment consists 16 blocks(i.e., 128 bytes of memory). 
	 * As a pre-condition, this command requires that the tag be in the READY state and afterward, the tag remains in READY state.
	 * The format of Header ROM(HRO=1yh, where y≠1) and memory structure for Dynamic memory is defined as below:
	 * HR0  HR1		
	 * 1yh  xxh
	 * @param DeviceAddress Device address of the reader.
	 * @param add The address of the segment of the memory. A segment consists of 16 blocks(i.e., 128 bytes of memory). 
	 * The format is defined as below:	
	 * _______________________________________________ 
	 * |Address operand ‘ADD’                         |
     * |Segment = select one of the Segements 0h – Fh |  
     * |msb                  lsb                      |
     * |b8  b7  b6  b5  b4  b3  b2  b1                |
     * |0b  Static Bloc 0b  ob  0b  0b                | 
     * |______________________________________________|
	 * @param uid The data received from polling command.(UID of the card).
	 * @param RetData The Data part contains 128 bytes data( 128 bytes for one segment).
	 * @return 0 - Successful.
	 */
	
	public native static int NFC_T1T_Rseg(int DeviceAddress, byte add,  byte [] uid, byte [] RetData); 
	
	/**
	 * The API is used to read-out a block of memory(8 bytes). As a pre-condition, 
	 * this command requires that the tag be in the READY state and afterward, 
	 * the tag remains in READY state.
	 * @param DeviceAddress Device address of the reader.
	 * @param The address of the block which specified to read-out.
     * The format is defined as below:
	 * _______________________________________________ 
	 * |Address operand ‘ADD8’                        |
     * |Segment=select one of 8-bytes blocks 00h–FFh  |  
     * |msb                  lsb                      |
     * |b8  b7  b6  b5  b4  b3  b2  b1                |
     * |             Global Block                     | 
     * |______________________________________________|
	 * @param uid The data received from polling command.(UID of the card).
	 * @param RetData The Data part contains the data for one block(8 bytes).
	 * @return 0 - Successful.
	 */
	
	public native static int NFC_T1T_Read8(int DeviceAddress, byte add,  byte [] uid, byte [] RetData);
	
	/**
	 * The API is used to write with erase to a block of memory. As a pre-condition, 
	 * this command requires that the tag be in the READY state and afterward, 
	 * the tag remains in READY state.
	 * @param DeviceAddress Device address of the reader.
	 * @param add The address of the block which specified to write to. 
	 * The format is defined as below:
	 * _______________________________________________ 
	 * |Address operand ‘ADD8’                        |
     * |Segment=select one of 8-bytes blocks 00h–FFh  |  
     * |msb                  lsb                      |
     * |b8  b7  b6  b5  b4  b3  b2  b1                |
     * |             Global Block                     | 
     * |______________________________________________|
	 * @param uid The data received from polling command.(UID of the card).
	 * @param RetData The Data part contains the data same as “data”.
	 * @return 0 - Successful.
	 */
	
	public native static int NFC_T1T_Write_E8(int DeviceAddress, byte add, byte [] data, byte [] uid, byte [] RetData);
	
	/**
	 * The API is used to write with no erase to a block of memory. As a pre-condition,
	 * this command requires that the tag be in the READY state and afterward, 
	 * the tag remains in READY state.
	 * @param DeviceAddress Device address of the reader.
	 * @param add The address of the block which specified to write to.
	 * The format is defined as below:
	 * _______________________________________________ 
	 * |Address operand ‘ADD8’                        |
     * |Segment=select one of 8-bytes blocks 00h–FFh  |  
     * |msb                  lsb                      |
     * |b8  b7  b6  b5  b4  b3  b2  b1                |
     * |             Global Block                     | 
     * |______________________________________________|
	 * @param uidThe data received from polling command.(UID of the card).
	 * @param RetData The Data part contains the data same as “data”.
	 * @return 0 - Successful.
	 */
	
	public native static int NFC_T1T_Write_Ne8(int DeviceAddress, byte add, byte [] data, byte [] uid, byte [] RetData);
	
	/**
	 * The API is used to read user data from the memory of a Type3 Tag.
	 * @param DeviceAddress Device address of the reader.
	 * @param uid The data received from polling command.(UID of the card).
	 * @param svc Service Code Number. It depends on the implementation of the Type3 Tag how many Services can be read simultaneously. 
	 * If the value is 0x01, sc should contains one Service Code, if the value is 0x02, 
	 * sc should contains two different Service Codes.
	 * @param sc Service Code List. Each Service Code is specified in Little Endian format. 
	 * Each Service Code value should be unique inside the Service Code List.
	 * @param blk Numbers of Blocks. 
	 * It depends on the implementation of the Type3 Tag how many blocks can be read simultaneously. If the value is 0x02,
	 * bl should contains two different block numbers.
	 * @param flag2 0x01 - indicate the size of Block List Element is two bytes.
	 *              0x00 – indicate the size of Block List Element is three bytes.
	 * @param bl Block list. It contains Block List Element. flag2 indicate the size of Block List Element. 
	 * @param RetData The Data1 part contains two part: Number of Blocks and Block Data.
	 *		Number of Blocks should be same as blk(input parameter).
   	 *		Block Data: the length should be 16 * Number of Blocks.
	 * @return 0 - Successful.
	 */
		
	public native static int NFC_T3T_Check(int DeviceAddress, byte [] uid, byte svc, byte [] sc, byte blk, byte flag2, byte [] bl, byte [] RetData);
	/**
	 * The API is used to write user data to the memory blocks of a Type3 Tag.
	 * @param DeviceAddress Device address of the reader.
	 * @param uid The data received from polling command.(UID of the card).
	 * @param svc Service Code Number. It depends on the implementation of the Type3 Tag how many Services can be read simultaneously. 
	 * If the value is 0x01, sc should contains one Service Code, if the value is 0x02, 
	 * sc should contains two different Service Codes.
	 * @param sc Service Code List. Each Service Code is specified in Little Endian format. 
	 * Each Service Code value should be unique inside the Service Code List.
	 * @param blk Numbers of Blocks. 
	 * It depends on the implementation of the Type3 Tag how many blocks can be read simultaneously. If the value is 0x02,
	 * bl should contains two different block numbers.
	 * @param flag2 0x01 - indicate the size of Block List Element is two bytes.
	 *              0x00 – indicate the size of Block List Element is three bytes.
	 * @param bl Block list. It contains Block List Element. flag2 indicate the size of Block List Element.
	 * @param data The data which used to write into the memory blocks. The length should be 16 * Number of Blocks.
	 * @param RetData It does not have the Data1 part.
	 * @return 0 - Successful.
	 */
	
	public native static int NFC_T3T_Update(int DeviceAddress, byte [] uid, byte svc, byte [] sc, byte blk, byte flag2, byte [] bl, byte [] data, byte [] RetData);
			
	/**
	 * The API is used to select file for the subsequence operations like Read and Write. 
	 * When completed, the command opens the logical channel numbered in CLA,if not yet
	 * opened, and sets a current structure within that logical channel. Subsequent commands
	 * may implicityly refer to the current structure through that logical channel.
	 * @param DeviceAddress Device address of the reader.
	 * @param P1 The format for P1 is defined as below
	 * b8  b7  b6  b5  b4  b3  b2  b1                      Meaning
	 * 0   0   0   0   0   0   x   x              Select
	 * 0   0   0   0   0   0   0   0              Select MF, DF or EF
	 * 0   0   0   0   0   0   0   1              Select child DF
	 * 0   0   0   0   0   0   1   0              Select EF under the current DF
	 * 0   0   0   0   0   0   1   1              Select parent DF of the current DF
	 * 
	 * 0   0   0   0   0   1   x   x              Selection by DF name
	 * 0   0   0   0   0   1   0   0              Select by DF name
	 * 
	 * 0   0   0   0   1   0   x   x              Selection by path
	 * 0   0   0   0   1   0   0   0              Select from the MF
	 * 0   0   0   0   1   0   0   1              Select from the current DF
	 * Any other value is reserved for future use by ISO/IEC JTC 1/SC 17.
	 * When present in the historical bytes or in EF.ATR, the first software function table indicates selection
	 * methods supported by the card.
	 * 
	 * @param P2 The format for P2 is defined as below
	 * b8  b7  b6  b5  b4  b3  b2  b1                     Meaning
	 * 0   0   0   0   -   -   x   x              File occurence
	 * 0   0   0   0   -   -   0   0              First or only occurrence
	 * 0   0   0   0   -   -   0   1              Last occurrence
	 * 0   0   0   0   -   -   1   0              Next occurrence
	 * 0   0   0   0   -   -   1   1              Previous occurrence
	 * 
	 * 0   0   0   0   x   x   -   -              File control information
	 * 0   0   0   0   0   0   -   -              -Return FCI template, optional use of FCI tag and length
	 * 0   0   0   0   0   1   -   -              -Return FCP template, mandatory use of FCP tag and length
	 * 0   0   0   0   1   0   -   -              -Return FMD template, mandatory use of FMD tag and length
	 * 0   0   0   0   1   1   -   -              -No response data if Le field absent, or proprietary if Le field present
	 * -Any other value is reserved for future use by ISO/IEC JTC 1/SC 17.
	 * 
	 * @param length Indicate the length of "data"
	 * @param data Indicate the file for selection
	 * @param RetData It does not contain "Data bytes" part, just has SW1 and SW2
	 * @return 0 - Successful.
	 */	
				
	public native static int NFC_T4TA_Select(int DeviceAddress, byte P1, byte P2, byte length, byte[] data, byte[] RetData);
	
	/**
	 * The API is used to read-out the content of an EF supporting data units with the specified length. 
	 * @param DeviceAddress Device address of the reader.
	 * @param P1,P2 The description is defined as below(The INS code for this command is 0xB0):
	 *        1)If bit 1 of INS is set to 0 and bit 8 of P1 to 1, then bits 7 and 6 of P1 are set to
	 *        00(RFU), bts 5 to 1 of P1 encode a short EF identifier and P2(eight bits)
	 *        encodes an offset from zero to 255.
	 *        2)If bit 1 of INS is set to 0 and bit 8 of P1 to 0, then P1-P2(fifteen bits)encodes an offset from zero to 32767
	 *        3)If bit 1 of INS is set to 1, then P1-P2 shall identify an EF. If the first eleven bits
	 *        of P1-P2 are set to 0 and if bits 5 to 1 of P2 are not all equal and if the card and / or
	 *        the EF supports selection by short EF identifier, then bits 5 to 1 of P2 encode a short EF 
	 *        identifier (a number from one to thirty). Otherwise, P1-P2 is a file identifier. P1-P2 set 
	 *        to '0000' identifies the current EF. At least on offset data object with tag '54' shall be 
	 *        present in the command data field. When present in a command or response data field, data 
	 *        shall be encapsulated in a discretionary data object with tag '53' or '73'.
	 * @param length The length of data which to be read-out
	 * @param RetData The Data part contains the data read-out from the file
	 * @return 0 - Successful.
	 */
	public native static int NFC_T4TA_Read(int DeviceAddress, byte P1, byte P2, byte length, byte[] RetData);
	
	/**
	 * The API write the specified data into the file. The command initiates the updata of bits 
	 * already present in an EF with the bits given in the data field. When it is completed,
	 * each bit of each specified data unit will have the value specified in the data field. 
	 * @param DeviceAddress Device address of the reader.
	 * @param P1,P2 The description is defined as below(The INS code for this command is 0xB0):
	 *        1)If bit 1 of INS is set to 0 and bit 8 of P1 to 1, then bits 7 and 6 of P1 are set to
	 *        00(RFU), bts 5 to 1 of P1 encode a short EF identifier and P2(eight bits)
	 *        encodes an offset from zero to 255.
	 *        2)If bit 1 of INS is set to 0 and bit 8 of P1 to 0, then P1-P2(fifteen bits)encodes an offset from zero to 32767
	 *        3)If bit 1 of INS is set to 1, then P1-P2 shall identify an EF. If the first eleven bits
	 *        of P1-P2 are set to 0 and if bits 5 to 1 of P2 are not all equal and if the card and / or
	 *        the EF supports selection by short EF identifier, then bits 5 to 1 of P2 encode a short EF 
	 *        identifier (a number from one to thirty). Otherwise, P1-P2 is a file identifier. P1-P2 set 
	 *        to '0000' identifies the current EF. At least on offset data object with tag '54' shall be 
	 *        present in the command data field. When present in a command or response data field, data 
	 *        shall be encapsulated in a discretionary data object with tag '53' or '73'.
	 * @param length The length of data to write to the file
	 * @param data The content of value need to write to the file.
	 * @param RetData The Data1 part is absent, only contains SW1 and SW2.
	 * @return 0 - Successful.
	 */
	public native static int NFC_T4TA_Write(int DeviceAddress, byte P1, byte P2, byte length, byte[] data, byte[] RetData);
	
	/**
	 * This API is use to set the working mode of the reader. If flg_save is 0x01, it indicates 
	the reader will goto Low consumption mode after 10s if there has no operations for the reader, the current is about 20mA.
	 * @param DeviceAddress Device address of the reader.
	 * @param flg_save 0x00 --- Normal mode.
					0x01 --- Low consumption mode.
	 * @return 0 - Successful.
	 */
	public native static int SetAutoPowerSave(int DeviceAddress, byte flg_save);
	
	/**
	 * The API is usually used to get the CIDs from the SRI type cards. Each SRI card have one unique CID, The CID is a random value between 0 and 255, The value changed every times.If return successful. You can use the CID and SRI_Select API to selected SRI type card.(only support CN370 and CN670).
	 * @param DeviceAddress Device Address of the reader.
	 * @param CIDLen The numbers of CIDs .
	 * @param CID Contains the card CIDs from Reader.
	 * @return 0 - Successful.
	 */
	public native static int SRI_Poll(int DeviceAddress, int[] CIDLen, byte[] CID);
	
	/**
	 * The API is used to select SRI card to subsequent operation. (only support CN370 and CN670).
	 * @param DeviceAddress Device Address of the reader.
	 * @param CID The selected CID value.
	 * @return 0 - Successful.
	 */
	public native static int SRI_Select(int DeviceAddress, int CID);
	
	/**
	 * The API is used to get UID from the SRI card. (only support CN370 and CN670).
	 * @param DeviceAddress Device Address of the reader.
	 * @param UID Unsigned char array with a length of 7, to store the UID which get from the SRI card.
	 * @return 0 - Successful.
	 */
	public native static int SRI_Get_UID(int DeviceAddress, byte[] UID); 
	
	/** 
	 * The API is used to read data from SRI card's block. (only support CN370 and CN670).
	 * @param DeviceAddress Device Address of the reader.
	 * @param BLK the block number which you want to read data from.
	 * @param Data The Data part contains the data for one block(4 bytes). 
	 * @return 0 - Successful.
	 */
	public native static int SRI_Read (int DeviceAddress, int BLK, byte[] Data);
	
	/**
	 * The API is used to write data to SRI card's block. (only support CN370 and CN670).
	 * @param DeviceAddress Device Address of the reader.
	 * @param BLK the block number which you want to write data in.
	 * @param Data The 4 bytes data used to write into the specified block.
	 * @return 0 - Successful.
	 */
	public native static int SRI_Write (int DeviceAddress, int BLK, byte[] Data); 
	
	/**
	 * The API is used to release the SRI card.Releasing a card means that the host controller has finished the communication with the cards(s), so the CNReader erases all the information relative to it(them). .
	 * @param DeviceAddress Device Address of the reader.
	 * @return 0 - Successful.
	 */
	public native static int SRI_Release (int DeviceAddress); 
	
}
