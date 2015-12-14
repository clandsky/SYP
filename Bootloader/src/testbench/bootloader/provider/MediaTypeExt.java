package testbench.bootloader.provider;

import javax.ws.rs.core.MediaType;
/**
 *   Created by Christoph Landsky and Sven Riedel (30.11.2015)
 */
public class MediaTypeExt extends MediaType {
    public final static String APPLICATION_PROTOBUF = "application/x-protobuf";
    public final static MediaType APPLICATION_PROTOBUF_TYPE = new MediaType("application", "x-protobuf");
}