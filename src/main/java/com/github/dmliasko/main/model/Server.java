package com.github.dmliasko.main.model;

import java.io.IOException;

/**
 * Created by dmlia on 21.06.2017.
 */
public interface Server {

    void run(CacheHandler handler, ResponseWriter writer) throws IOException;


}
