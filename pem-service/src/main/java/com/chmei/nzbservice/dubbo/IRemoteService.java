package com.chmei.nzbservice.dubbo;

import com.chmei.nzbcommon.cmbean.InputDTO;
import com.chmei.nzbcommon.cmbean.OutputDTO;


public interface IRemoteService {
	
    void execute(InputDTO inputDTO, OutputDTO outputDTO);
}
