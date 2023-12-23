package com.cyberlearn.osahaneat.service.imp;

import com.cyberlearn.osahaneat.payload.request.OrderRequest;

public interface OrderServiceImp {
    boolean insertOrder(OrderRequest orderRequest);
}
