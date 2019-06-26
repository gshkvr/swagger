package by.epam.kvirykashvili.javalabtask.service.impl;

import by.epam.kvirykashvili.javalabtask.domain.model.Tour;
import by.epam.kvirykashvili.javalabtask.domain.model.TourCsv;
import by.epam.kvirykashvili.javalabtask.domain.model.TourType;
import by.epam.kvirykashvili.javalabtask.repository.CountryRepository;
import by.epam.kvirykashvili.javalabtask.repository.HotelRepository;
import by.epam.kvirykashvili.javalabtask.repository.TourRepository;
import by.epam.kvirykashvili.javalabtask.service.ImportService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ImportServiceImpl implements ImportService {
    private CountryRepository countryRepository;
    private HotelRepository hotelRepository;
    private TourRepository tourRepository;
    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public ImportServiceImpl(CountryRepository countryRepository, HotelRepository hotelRepository, TourRepository tourRepository, SequenceGeneratorService sequenceGenerator) {
        this.countryRepository = countryRepository;
        this.hotelRepository = hotelRepository;
        this.tourRepository = tourRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void importToursFromCsv(String csvFile) {
        List<TourCsv> tourList = null;
        try {
            tourList = createToursFromFile(csvFile);
        } catch (IOException e) {
            log.warn("Problems with tours csv file", e);
        }
        if (tourList != null) {
            saveTours(tourList);
        }
    }

    private List<TourCsv> createToursFromFile(String csvFile) throws IOException {

        List<TourCsv> csvTours = new ArrayList<>();
        try (Reader reader = new StringReader(csvFile)) {
            CsvToBean<TourCsv> csvToBean = new CsvToBeanBuilder<TourCsv>(reader)
                    .withType(TourCsv.class)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            for (TourCsv csvTour : csvToBean) {
                csvTours.add(csvTour);
            }
        }
        return csvTours;
    }

    private void saveTours(List<TourCsv> tourCsvList) {
        for (TourCsv tourCsv : tourCsvList) {
            Tour tour = convertTourCsvToTour(tourCsv);
            tourRepository.save(tour);
        }
    }

    private Tour convertTourCsvToTour(TourCsv tourCsv) {
        return Tour.builder()
                .id(sequenceGenerator.generateSequence(Tour.SEQUENCE_NAME))
                .photo(tourCsv.getPhoto())
                .date(tourCsv.getDate())
                .duration(tourCsv.getDuration())
                .description(tourCsv.getDescription())
                .cost(tourCsv.getCost())
                .tourType(TourType.valueOf(tourCsv.getTourType()))
                .hotel(hotelRepository.findById(tourCsv.getHotelId()).get())
                .country(countryRepository.findById(tourCsv.getCountryId()).get())
                .build();
    }
}
