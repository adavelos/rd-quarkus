//package org.argonath.rd.rest;
//
//import javax.json.bind.Jsonb;
//import javax.json.bind.JsonbBuilder;
//import javax.json.bind.JsonbConfig;
//import javax.ws.rs.ext.ContextResolver;
//import javax.ws.rs.ext.Provider;
//
//@Provider
//public class JsonConfig implements ContextResolver<Jsonb> {
//    @Override
//    public Jsonb getContext(Class<?> aClass) {
//        JsonbConfig config = new JsonbConfig();
//        config.withSerializers(new DateSerializer());
//        config.withDeserializers(new DateSerializer());
//        return JsonbBuilder.create(config);
//    }
//}
