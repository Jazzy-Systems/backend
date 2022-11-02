package com.jazzysystems.backend.request.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jazzysystems.backend.auth.Authentication;
import com.jazzysystems.backend.exception.NoSuchElementFoundException;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.service.PersonService;
import com.jazzysystems.backend.request.Request;
import com.jazzysystems.backend.request.RequestMapper;
import com.jazzysystems.backend.request.dto.RequestDTO;
import com.jazzysystems.backend.request.repository.RequestRepository;
import com.jazzysystems.backend.typeRequest.TypeRequest;
import com.jazzysystems.backend.typeRequest.service.TypeRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    private final PersonService personService;
    private final TypeRequestService typeRequestService;

    private final Authentication authentication;

    @Override
    public Request saveRequest(RequestDTO requestDTO) {

        Person person = authentication.getAuthenticatedPerson();
        TypeRequest typeRequest = typeRequestService
                .findByTypeRequestName(requestDTO.getTypeRequest().getTypeRequestName());

        LocalDateTime dateRequest = LocalDateTime.now();
        requestDTO.setDateRequest(dateRequest);
        requestDTO.setStatusRequest(false);
        requestDTO.setPerson(person);
        requestDTO.setTypeRequest(typeRequest);

        Request request = requestMapper.convertDTOtoRequest(requestDTO);
        //To Do: Email
        return requestRepository.save(request);
    }

    @Override
    public Request updateRequest(Long requestId, RequestDTO requestDTO) {
        Request request = this.findByIdRequest(requestId);
        LocalDateTime dateResponse = LocalDateTime.now();
        request.setDateResponse(dateResponse);
        request.setStatusRequest(true);
        request.setResponseRequest(requestDTO.getResponseRequest());

        return requestRepository.save(request);
    }

    @Override
    public void deleteRequest(RequestDTO requestDTO) {
        Request request = this.findByIdRequest(requestDTO.getRequestId());
        requestRepository.delete(request);
    }

    @Override
    public void deleteByIdRequest(Long requestId) {
        Request request = this.findByIdRequest(requestId);
        requestRepository.delete(request);
    }

    @Override
    public Request findByIdRequest(Long requestId) {
        Request request = requestRepository.findById(
                requestId).orElseThrow(
                        () -> new NoSuchElementFoundException("Request Not Found"));
        return request;
    }

    @Override
    public List<Request> findAllRequest(){
        return requestRepository.findAll(Sort.by(Sort.Direction.DESC, "dateRequest"));
    }

    @Override
    public List<Request> findAllRequestsByStatusRequest(Boolean statusRequest){
        return requestRepository.findByStatusRequest(statusRequest);
    }

    @Override   
    public List<Request> findRequestsByPerson(Long persondni){
        Person person = personService.findPersonByDni(persondni);
        return requestRepository.findAllByPerson(person);
    }

    @Override
    public List<Request> findAllByPersonAndStatusRequest(Person person, Boolean statusRequest){
        return requestRepository.findAllByPersonAndStatusRequest(person,statusRequest);
    }

    @Override
    public Boolean existsRequestById(Long requestId){
        return requestRepository.existsById(requestId);
    }
}
