package kr.co.basedevice.corebase.service;

import java.util.List;

import kr.co.basedevice.corebase.domain.cm.Resources;

public interface ResourcesService {

    Resources getResources(long id);

    List<Resources> getResources();

    void createResources(Resources Resources);

    void deleteResources(long id);
}