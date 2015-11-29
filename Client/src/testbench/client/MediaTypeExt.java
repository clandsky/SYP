package testbench.client;

import javax.ws.rs.core.MediaType;

public class MediaTypeExt extends MediaType {
    public final static String APPLICATION_PROTOBUF = "application/x-protobuf";
    public final static MediaType APPLICATION_PROTOBUF_TYPE = new MediaType("application", "x-protobuf");

}