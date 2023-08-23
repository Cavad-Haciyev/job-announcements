package com.example.jobannouncement.model;

import com.example.jobannouncement.model.enums.CommunicationTool;
import com.example.jobannouncement.model.enums.Currency;
import com.example.jobannouncement.model.enums.Education;
import com.example.jobannouncement.model.enums.WorkGraphic;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vacancy {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String applicantName;
  private String phone;
  private String email;
  @Enumerated(EnumType.STRING)
  private CommunicationTool communicationTool;
  private String vacancyName;
  @ManyToOne
  @JoinColumn(name="category_id", nullable=false)
  private Category category;
  private String city;
  private String address;
  private BigDecimal minSalary;
  private BigDecimal maxSalary;
  @Enumerated(EnumType.STRING)
  private Currency currency;
  @Enumerated(EnumType.STRING)
  private WorkGraphic workGraphic;
  private LocalDateTime expireDate;
  private String specialKnowledge;
  private String textOfTheVacancy;
  private String specialRequirements;
  private String vacancyEmail;
  private String minExperience;
  @Enumerated(EnumType.STRING)
  private Education minEducation;



}
