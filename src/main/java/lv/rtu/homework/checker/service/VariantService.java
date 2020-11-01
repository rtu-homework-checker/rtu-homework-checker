package lv.rtu.homework.checker.service;

import lv.rtu.homework.checker.domain.Variant;
import lv.rtu.homework.checker.repository.VariantRepository;
import lv.rtu.homework.checker.service.dto.VariantDTO;
import lv.rtu.homework.checker.service.mapper.VariantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Variant}.
 */
@Service
@Transactional
public class VariantService {

    private final Logger log = LoggerFactory.getLogger(VariantService.class);

    private final VariantRepository variantRepository;

    private final VariantMapper variantMapper;

    public VariantService(VariantRepository variantRepository, VariantMapper variantMapper) {
        this.variantRepository = variantRepository;
        this.variantMapper = variantMapper;
    }

    /**
     * Save a variant.
     *
     * @param variantDTO the entity to save.
     * @return the persisted entity.
     */
    public VariantDTO save(VariantDTO variantDTO) {
        log.debug("Request to save Variant : {}", variantDTO);
        Variant variant = variantMapper.toEntity(variantDTO);
        variant = variantRepository.save(variant);
        return variantMapper.toDto(variant);
    }

    /**
     * Get all the variants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VariantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Variants");
        return variantRepository.findAll(pageable)
            .map(variantMapper::toDto);
    }


    /**
     * Get one variant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VariantDTO> findOne(Long id) {
        log.debug("Request to get Variant : {}", id);
        return variantRepository.findById(id)
            .map(variantMapper::toDto);
    }

    /**
     * Delete the variant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Variant : {}", id);
        variantRepository.deleteById(id);
    }
}
