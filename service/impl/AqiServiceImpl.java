package com.postgresql.aqi.service.impl;

import com.postgresql.aqi.dto.AqiDto;
import com.postgresql.aqi.entity.Aqi;
import com.postgresql.aqi.exception.ResourceNotFoundException;
import com.postgresql.aqi.mapper.AqiMapper;
import com.postgresql.aqi.repository.AqiRepository;
import com.postgresql.aqi.service.AqiService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AqiServiceImpl implements AqiService {

    @Autowired
    private final AqiRepository aqiRepository;

    @Autowired
    private EntityManager entityManager;

//    @Transactional
//    public Aqi mergeAqi(Aqi aqi) {
//        // Merging the detached entity back into the current session
//        return entityManager.merge(aqi);
//    }

    public AqiServiceImpl(AqiRepository aqiRepository) {
        this.aqiRepository = aqiRepository;
    }

    @Override
    public AqiDto createAqi(AqiDto aqiDto) {
        if (aqiDto == null) {
            throw new IllegalArgumentException("AqiDto cannot be null");
        }
        Aqi aqi = AqiMapper.mapToAqi(aqiDto);
//        aqi.setAqiId(1L);
//        aqi.setVersion(1L);
        Aqi savedAqi = aqiRepository.save(aqi);
        //Aqi aqi = new Aqi();
         // Set the version manually if you know the current version
// Perform save or update

        return AqiMapper.mapToAqiDto(savedAqi);
    }

    @Override
    public AqiDto getAqi(Long aqiId) {
        Aqi aqi=aqiRepository.findById(aqiId)
                .orElseThrow(()-> new ResourceNotFoundException("AQI Data does not exist for the given ID."));
        return AqiMapper.mapToAqiDto(aqi);
    }

    @Override
    public List<AqiDto> getAllAQIs() {
        List<Aqi> aqi=aqiRepository.findAll();
        return aqi.stream().map((aqi1)->AqiMapper.mapToAqiDto(aqi1)).collect(Collectors.toList());
    }

    @Override
    public void deleteAQI(Long aqiId) {
        Aqi aqi=aqiRepository.findById(aqiId)
                .orElseThrow(()-> new ResourceNotFoundException("AQI Data does not exist for the given ID."));
        aqiRepository.deleteById(aqiId);
    }
}
