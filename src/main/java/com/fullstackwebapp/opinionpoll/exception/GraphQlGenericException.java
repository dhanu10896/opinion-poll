package com.fullstackwebapp.opinionpoll.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphQlGenericException extends RuntimeException implements GraphQLError {

    private ErrorType errorType;
    Map<String, Object> extentions;


    public GraphQlGenericException(ErrorType errorType) {
        super(errorType.name());
        this.errorType = errorType;
        extentions = new HashMap<>();
    }

    public Map<String, Object> addInfo(String key, Object object) {
        this.extentions.put(key, object);
        return this.extentions;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return errorType;
    }

    @Override
    public List<Object> getPath() {
        return null;
    }

    @Override
    public Map<String, Object> toSpecification() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extentions;
    }
}
