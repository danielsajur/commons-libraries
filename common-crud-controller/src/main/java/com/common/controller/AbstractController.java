package com.common.controller;

import com.common.crud.service.AbstractService;
import com.common.crud.entity.Entity;
import com.common.request.Request;
import com.common.response.Response;
import com.common.response.ResponseBuilder;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractController<ID extends Number, E extends Entity, R extends Request>{

    private static final int INDEX_ARGUMENT_OF_E = 1;
    private static final int INDEX_ARGUMENT_OF_R = 2;

    @Value("{message.deleted.successfully}")
    protected String MESSAGE_OF_DELETED_SUCCESS;

    protected final AbstractService<ID,E> service;

    public <S> AbstractController(@Autowired AbstractService<ID,E> service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Response<R>> getAll() throws Exception {
        List<E> all = service.getAll();
        List<R> people = new ArrayList<>();
        all.forEach(E -> {
            R data = newInstanceR();
            BeanUtils.copyProperties(E, data);
            people.add(data);
        });
        return new ResponseBuilder<R>().withData(people).build();
    }

    @PostMapping
    public ResponseEntity<Response<R>> add(@Valid @RequestBody R request) {
        E E = newInstanceE();
        BeanUtils.copyProperties(request, E);
        E ESaved = service.save(E);
        R data = newInstanceR();
        BeanUtils.copyProperties(ESaved, data);
        return new ResponseBuilder<R>().withData(data).withHttp(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Response<R>> get(@PathVariable ID id) throws Exception {
        E E = service.getBy(id);
        R data = newInstanceR();
        BeanUtils.copyProperties(E, data);
        return new ResponseBuilder<R>().withData(data).build();
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Response<R>> delete(@PathVariable ID id) throws Exception {
        E E = service.getBy(id);
        service.delete(E);
        return new ResponseBuilder<R>().withMessage(MESSAGE_OF_DELETED_SUCCESS).withHttp(HttpStatus.NO_CONTENT).build();
    }

    private <T> T instance(final int index) {

        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        Type instanceType = paramType.getActualTypeArguments()[index];
        try {
            return ((Class<T>) instanceType).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private R newInstanceR() {
        return instance(INDEX_ARGUMENT_OF_R);
    }

    private E newInstanceE() {
        return instance(INDEX_ARGUMENT_OF_E);
    }
}
