package com.jazzysystems.backend.serviceTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.dto.PersonDTO;
import com.jazzysystems.backend.person.service.PersonService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
// @DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// //For modify the real database
public class PersonTest {

    @Autowired
    private PersonService personService;

    @Test
    public void testSavePerson() {
        PersonDTO personDTO = new PersonDTO("Sebastian", "Hernandez", 123369517, 320479509, "newemail@email.com");
        Person person = personService.savePerson(personDTO);
        assertNotNull(person);
        assertEquals(123369517, person.getDni());
        assertNotNull(person.getDni());
    }
}
