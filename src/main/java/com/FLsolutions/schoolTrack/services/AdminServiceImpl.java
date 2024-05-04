package com.FLsolutions.schoolTrack.services;

import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{
	
	private AdminRepository adminRepository;

	public AdminServiceImpl(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}
	
	

}
