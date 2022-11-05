package com.jazzysystems.backend.pack.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jazzysystems.backend.auth.Authentication;
import com.jazzysystems.backend.exception.NoSuchElementFoundException;
import com.jazzysystems.backend.pack.Pack;
import com.jazzysystems.backend.pack.PackMapper;
import com.jazzysystems.backend.pack.dto.PackDTO;
import com.jazzysystems.backend.pack.repository.PackRepository;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.service.PersonService;
import com.jazzysystems.backend.person.service.PersonServiceImpl;
import com.jazzysystems.backend.securityguard.SecurityGuard;
import com.jazzysystems.backend.securityguard.service.SecurityGuardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PackServiceImpl implements PackService {
    private final PackRepository packRepository;
    private final PackMapper packMapper;

    @Autowired
    private final Authentication authentication;

    @Autowired
    private final SecurityGuardService securityGuardService;

    @Autowired
    private final PersonService personService;

    @Autowired
    private final PersonServiceImpl personServiceImpl;

    @Override
    public Pack savePack(PackDTO packDTO) {
        Person securityPerson = authentication.getAuthenticatedPerson();
        SecurityGuard securityGuard = securityGuardService.findByPerson(securityPerson);
        Person person = personService.findById(packDTO.getPersonDTO().getPersonId());
        Pack pack = packMapper.convertDTOtoPack(packDTO);
        LocalDateTime dateArrival = LocalDateTime.now();

        pack.setPerson(person);
        pack.setSecurityGuard(securityGuard);
        pack.setDateArrival(dateArrival);
        pack.setReceived(false);

        return packRepository.save(pack);
    }

    @Override
    public Pack updatePack(Long packId, PackDTO packDTO) {
        Pack pack = this.findByIdPack(packId);
        LocalDateTime datePickedUp = LocalDateTime.now();
        pack.setDatePickedUp(datePickedUp);
        pack.setReceived(true);
        pack.setObservation(packDTO.getObservation());
        return packRepository.save(pack);
    }

    @Override
    public void deletePack(PackDTO packDTO) {
        Pack pack = this.findByIdPack(packDTO.getPackId());
        packRepository.delete(pack);
    }

    @Override
    public void deleteByIdPack(Long packId) {
        Pack pack = this.findByIdPack(packId);
        packRepository.delete(pack);
    }

    @Override
    public Pack findByIdPack(Long packId) {
        Pack pack = packRepository.findById(
                packId).orElseThrow(
                        () -> new NoSuchElementFoundException("Pack Not Found"));
        return pack;
    }

    @Override
    public List<PackDTO> findAllPack() {
        List<Pack> listPack = packRepository.findAll(Sort.by(Sort.Direction.DESC, "dateArrival"));
        List<PackDTO> listPackDTO = new ArrayList<PackDTO>();
        Integer numPackMax = 1000, numPack = 0;
        for (Pack pack : listPack) {
            listPackDTO.add(packMapper.convertPackToDTO(pack));
            numPackMax++;
            if (numPack > numPackMax)
                break;
        }
        return listPackDTO;
    }

    @Override
    public List<PackDTO> findAllPackApartament(Long apartmentId) {
        List<Person> listResidenAparment = personServiceImpl.findAllResident(apartmentId);
        List<Pack> listPacks = new ArrayList<Pack>();
        for (Person person : listResidenAparment) {
            List<Pack> listPackPerson = packRepository.getFindByPersonAndReceived(person, false);
            listPacks.addAll(listPackPerson);
        }

        List<PackDTO> listPackDTO = new ArrayList<PackDTO>();
        for (Pack pack : listPacks) {
            listPackDTO.add(packMapper.convertPackToDTO(pack));
        }
        return listPackDTO;
    }

    @Override
    public List<Pack> findAllPackByReceived(Boolean received) {
        return packRepository.findByReceived(received);
    }

    @Override
    public List<Pack> findPacksByPerson(Person person) {
        return packRepository.findByPerson(person);
    }

    @Override
    public Boolean existsPackById(Long packId) {
        return packRepository.existsById(packId);
    }

}
