package com.example.bff.api.base;

public interface OperationProcessor<Input, Response> {
    Response process(Input input);

}
