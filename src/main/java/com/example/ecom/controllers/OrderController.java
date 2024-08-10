package com.example.ecom.controllers;

import com.example.ecom.dtos.CancelOrderRequestDto;
import com.example.ecom.dtos.CancelOrderResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.exceptions.OrderCannotBeCancelledException;
import com.example.ecom.exceptions.OrderDoesNotBelongToUserException;
import com.example.ecom.exceptions.OrderNotFoundException;
import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {
    @Autowired
    private OrderService service;
    public CancelOrderResponseDto cancelOrder(CancelOrderRequestDto cancelOrderRequestDto) {

        CancelOrderResponseDto responseDto = new CancelOrderResponseDto();
        try{
            responseDto.setOrder(service.cancelOrder(cancelOrderRequestDto.getOrderId(), cancelOrderRequestDto.getUserId()));
            responseDto.setStatus(ResponseStatus.SUCCESS);
        }catch(UserNotFoundException | OrderNotFoundException | OrderDoesNotBelongToUserException | OrderCannotBeCancelledException e){
            responseDto.setStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }

}
