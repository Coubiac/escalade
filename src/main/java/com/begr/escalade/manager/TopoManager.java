package com.begr.escalade.manager;

import com.begr.escalade.entity.Reservation;
import com.begr.escalade.entity.ReservationStatus;
import com.begr.escalade.entity.Topo;
import com.begr.escalade.repository.TopoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TopoManager {

    @Resource
    TopoRepository topoRepository;

    public List<Topo> getFreeTopos(){
        List<Topo> freeTopo = new ArrayList<>();

        for(Topo topo: topoRepository.findAll()){
            Boolean isFree = true;
            for(Reservation reservation: topo.getReservations()){
                if(reservation.getStatus() == ReservationStatus.EN_COURS){
                    isFree = false;
                }
            }
            if(isFree){
                freeTopo.add(topo);
            }
        }

        return freeTopo;
    }
}
