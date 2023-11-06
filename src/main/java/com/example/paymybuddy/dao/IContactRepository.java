package com.example.paymybuddy.dao;

import com.example.paymybuddy.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IContactRepository extends JpaRepository<Contact, String> {
   public Page<Contact> findByUserEmail(String userEmail, Pageable pageable);
   public List<Contact>findByUserEmail(String userEmail);
    public void deleteByContactEmail(String email);
}
