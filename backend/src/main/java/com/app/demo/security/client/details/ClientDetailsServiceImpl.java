package com.app.demo.security.client.details;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.demo.model.Reports;
import com.app.demo.repository.ReportRepository;

@Service
public class ClientDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private ReportRepository reportRepository;;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String reportId) throws UsernameNotFoundException {
        Reports report = reportRepository.findByReportId(reportId)
            .orElseThrow(() -> new UsernameNotFoundException("Report with current id cannot be found"));

        return ClientDetailsImpl.build(report);
    }
}
