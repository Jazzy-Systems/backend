package com.jazzysystems.backend.request.service;

import java.util.List;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.request.Request;
import com.jazzysystems.backend.request.dto.RequestDTO;

public interface RequestService {
    Request saveRequest(RequestDTO requestDTO);

    Request updateRequest(Long requestId, RequestDTO requestDTO);

    void deleteRequest(RequestDTO requestDTO);

    void deleteByIdRequest(Long requestId);

    Request findByIdRequest(Long requestId);

    List<Request> findAllRequest();

    List<Request> findAllRequestsByStatusRequest(Boolean statusRequest);

    List<Request> findRequestsByPerson(Long persondni);

    List<Request> findAllByPersonAndStatusRequest(Person person, Boolean statusRequest);

    Boolean existsRequestById(Long requestId);
}
