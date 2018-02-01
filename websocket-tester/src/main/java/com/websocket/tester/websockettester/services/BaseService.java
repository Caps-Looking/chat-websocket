package com.websocket.tester.websockettester.services;

import com.websocket.tester.websockettester.models.dtos.ControllerResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<T> {

    public abstract JpaRepository<T, Long> getRepository();

    public ControllerResponse save(T entity) {
        ControllerResponse controllerResponse = new ControllerResponse();
        try {
           controllerResponse.setData(getRepository().save(entity));
           controllerResponse.setMessage("Saved successfully");
           controllerResponse.setMessageType("success");
        } catch (Exception e) {
           controllerResponse.setMessage("Problem on save");
           controllerResponse.setMessageType("danger");
        }
        return controllerResponse;
    }

}
