package vn.tcx.demo.springbatchexport.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import vn.tcx.demo.springbatchexport.constants.NhanVienConstants;
import vn.tcx.demo.springbatchexport.domain.NhanVien;
import vn.tcx.demo.springbatchexport.mapper.NhanVienRowMapper;
import vn.tcx.demo.springbatchexport.processor.NhanVienItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchExportConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<NhanVien> itemReader() {
        JdbcCursorItemReader<NhanVien> itemReader = new JdbcCursorItemReader<NhanVien>();

        itemReader.setDataSource(dataSource);
        itemReader.setSql(NhanVienConstants.QUERY_SELECT_NHAN_VIEN);
        itemReader.setRowMapper(new NhanVienRowMapper());

        return itemReader;
    }

    @Bean
    public NhanVienItemProcessor itemProcessor() {
        return new NhanVienItemProcessor();
    }

    @Bean
    public FlatFileItemWriter<NhanVien> itemWriter() {
        FlatFileItemWriter<NhanVien> itemWriter = new FlatFileItemWriter<NhanVien>();

        itemWriter.setResource(new ClassPathResource(NhanVienConstants.FILE_DATA));

        DelimitedLineAggregator<NhanVien> lineAggregator = new DelimitedLineAggregator<NhanVien>();
        lineAggregator.setDelimiter(",");

        BeanWrapperFieldExtractor<NhanVien> fieldExtractor = new BeanWrapperFieldExtractor<NhanVien>();
        fieldExtractor.setNames(
                new String[] { "maNhanVien", "tenNhanVien", "email", "soDienThoai" });

        lineAggregator.setFieldExtractor(fieldExtractor);
        itemWriter.setLineAggregator(lineAggregator);

        return itemWriter;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<NhanVien, NhanVien> chunk(500)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job exportCSVJob() {
        return jobBuilderFactory.get("exportCSVJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }
}
