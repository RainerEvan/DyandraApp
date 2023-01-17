package com.project.dyandra.exception;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class AbstractGraphQLException extends RuntimeException implements GraphQLError{

    private String invalidField;

    public AbstractGraphQLException(String message){
        super(message);
    }

    public AbstractGraphQLException(String message, String additionParam){
        super(message);
        if(additionParam!=null){
            this.invalidField = additionParam;
        }
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    @JsonIgnore
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    public ErrorType getErrorType() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Collections.singletonMap("invalidField", invalidField);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }
}
