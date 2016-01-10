package testbench.bootloader.provider;

import javax.ws.rs.core.MediaType;
/**
 *   Diese Klasse dient lediglich als Container für die Definition des MediaType "Application_Protobuf".
 *   Dies kann zum Beispiel in der Annotation der RestResource verwendet werden.
 */
public class MediaTypeExt extends MediaType {
    public final static String APPLICATION_PROTOBUF = "application/x-protobuf";
    public final static MediaType APPLICATION_PROTOBUF_TYPE = new MediaType("application", "x-protobuf");
}