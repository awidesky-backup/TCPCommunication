package com.awidesky.tcpChannelCommunication.shared;

import java.nio.charset.Charset;

/**
 * <pre>
 * com.awidesky.tcpChannelCommunication.shared
 * Protocole.java
 * </pre>
 * 
 * @author  : Eugene Hong
 * @date    : 2020. 3. 31. 
 * @version : 1.0
 * 
 * 
 * <p>Class for declare basic rule for sending data through <code>TCPCommunication</code>
 * <p>Version of <code>Protocole</code> must be same between <code>server</code> and <code>client</code>
 */

public final class Protocole {

	public static final Charset CHARSET = Charset.forName("UTF-16");
	
}
