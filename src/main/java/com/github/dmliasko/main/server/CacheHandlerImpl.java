package com.github.dmliasko.main.server;

import com.github.dmliasko.main.model.impl.HTTPResponse;
import com.github.dmliasko.main.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dmlia on 21.06.2017.
 */
public class CacheHandlerImpl implements CacheHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHeader.class);

    private ConcurrentHashMap<String, SoftReference<Response>> myCache = new ConcurrentHashMap<>();

    private MethodHandler methodHandler = new MethodHandlerImpl();

    @Override
    public Response apply(Request request) {
        SoftReference<Response> response = myCache.get(request.getRequestData());
        if(response != null){
            Response iResponse = response.get();
            try{
                iResponse.setSocket(request.getClient());
                return iResponse;
            } catch (NullPointerException ignored){
            }
        } else{
            Response iResponse = new HTTPResponse(request.getClient(), request.getRequestHeader());

            methodHandler.handle(iResponse);

            SoftReference<Response> softReference = new SoftReference<>(iResponse);
            if(softReference.get().isCacheable()) {
                myCache.put(request.getRequestData(), softReference);
            }
            return softReference.get();
        }
        return null;
    }

}
