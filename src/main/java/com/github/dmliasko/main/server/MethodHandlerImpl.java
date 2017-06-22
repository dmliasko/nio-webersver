package com.github.dmliasko.main.server;

import com.github.dmliasko.main.model.MethodHandler;
import com.github.dmliasko.main.model.Response;
import com.github.dmliasko.main.util.RootFolder;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by dmlia on 21.06.2017.
 */
public class MethodHandlerImpl implements MethodHandler {

    private Response response;

    private byte[] responseData;

    public MethodHandlerImpl() {
    }

    @Override
    public void handle(Response response) {
        this.responseData = "".getBytes();

        this.response = response;
        String method = response.getRequestHeader().getMethod();

        cutOffParamsAndReplaceSpaces();

        if (method.equals("HEAD")) {
            head();
        }
        if (method.equals("GET")) {
            get();
        }
        if (method.equals("DELETE")) {
            delete();
        }

        response.setResponseData(responseData);
    }

    private void get() {
        Path fullPath = Paths.get(RootFolder.filePath
                + response.getRequestHeader().getLocation());

        try {
            if (Files.exists(fullPath)) {
                responseData = Files.readAllBytes(fullPath);
                response.getResponseHeader().setContentLength(responseData.length);
            } else {
                notFoundResponse();
            }
        }
        catch (AccessDeniedException e){
            forbiddenResponse();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void head() {
    }

    private void delete() {
        Path fullPath = Paths.get(RootFolder.filePath
                + response.getRequestHeader().getLocation());

        try {
            if (Files.exists(fullPath)) {
                Files.delete(fullPath);
            } else {
                notFoundResponse();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notFoundResponse() {
        response.getResponseHeader().setResponseCode(404);
        response.getResponseHeader().setResponseReason("Not found");
        responseData = "Not Found".getBytes();
    }

    private void forbiddenResponse(){
        response.getResponseHeader().setResponseCode(403);
        response.getResponseHeader().setResponseReason("Forbidden");
        responseData = "Forbidden".getBytes();
    }

    private void cutOffParamsAndReplaceSpaces() {
        String location = response.getRequestHeader().getLocation().replaceAll("%20", " ");
        if (location.contains("?")) {
            int index = location.lastIndexOf("?");
            response.getRequestHeader().setLocation(location.substring(0, index));
        }
    }
}
