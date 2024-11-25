package com.book.address.exception;

public class AddressBookNotFoundException extends RuntimeException
{
    public AddressBookNotFoundException(String message)
    {
        super(message);
    }
}
