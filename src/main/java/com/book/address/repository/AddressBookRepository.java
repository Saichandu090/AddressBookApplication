package com.book.address.repository;

import com.book.address.model.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook,Integer>
{
    Optional<AddressBook> findByFullName(String fullName);
}
