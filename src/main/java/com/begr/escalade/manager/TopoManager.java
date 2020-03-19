package com.begr.escalade.manager;

import com.begr.escalade.entity.Topo;
import com.begr.escalade.repository.TopoRepository;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class TopoManager {

    @Resource
    TopoRepository topoRepository;

    @Resource
    UserManager userManager;

    public List<Topo> getFreeTopos(){
        Integer theConnectedUserId = userManager.getTheConnectedUser().getId().intValue();
        return topoRepository.findAllDispo(theConnectedUserId);
    }

}
