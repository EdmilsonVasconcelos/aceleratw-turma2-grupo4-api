package com.tw.acelera.grupo4.aceleratwturma2grupo4api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.user.request.ChangePasswordRequestDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.user.request.SaveUserDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.dto.user.response.UserSavedDTO;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception.UserExistException;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.exception.UserNotExistException;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.model.User;
import com.tw.acelera.grupo4.aceleratwturma2grupo4api.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	private static final String USER_WITH_EMAIL_EXIST = "User with email %s exist.";
	
	private static final String USER_WITH_EMAIL_NOT_EXIST = "User with email %s not exist.";
	
	private static final String USER_WITH_ID_NOT_EXIST = "User with id %s not exist.";
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<UserSavedDTO> getAllUsers() {
		
		log.debug("UserService.getAllUsers - Start - Request:  [{}]");
		
		List<User> users = userRepository.findAll();
		
		List<UserSavedDTO> response = new ArrayList<>();
		
		users.forEach(user -> {
			UserSavedDTO userSaved = mapper.map(user, UserSavedDTO.class);
			
			response.add(userSaved);
		});
		
		
		log.debug("UserService.getAllUsers - Finish - Response:  [{}]", response);
		
		return response;
		
	}
	
	public UserSavedDTO saveUser(SaveUserDTO request) {
		
		log.debug("UserService.saveUser - Start - Request:  [{}]", request);
		
		checkExistUser(request.getEmail());
		
		User userToSave = mapper.map(request, User.class);
		
		userToSave.setPassword(new BCryptPasswordEncoder().encode(userToSave.getPassword().toString()));
		
		User userSaved = userRepository.save(userToSave);
		
		UserSavedDTO response = mapper.map(userSaved, UserSavedDTO.class);
		
		log.debug("UserService.saveUser - Finish - Request:  [{}], Response:  [{}]", request, response);
		
		return response;
		
	}
	
	public UserSavedDTO changePassword(ChangePasswordRequestDTO request) {
		
		log.debug("UserService.updateUser - Start - Request");
		
		String userLogged = getEmailUserLogged();
		
		User userTosave = getUserByEmail(userLogged);
		
		userTosave.setPassword(new BCryptPasswordEncoder().encode(request.getPassword().toString()));	
		
		User userSaved = userRepository.save(userTosave);
		
		UserSavedDTO response = mapper.map(userSaved, UserSavedDTO.class);
		
		log.debug("UserService.updateUser - Finish - Request:  [{}], Response:  [{}]", response);
		
		return response;
			
		
	}
	
	private String getEmailUserLogged () {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
		    return ((UserDetails)principal).getUsername();
		} else {
		    return principal.toString();
		}
		
	}
	
	public void deleteUser(Long idUser) {
		
		log.debug("UserService.deleteUser - Start - idUser:  [{}]", idUser);
		
		checkExistUser(idUser);
		
		userRepository.deleteById(idUser);
		
		log.debug("UserService.deleteUser - Finish - idUser:  [{}]", idUser);
		
	}
	
	private User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistException(String.format(USER_WITH_EMAIL_NOT_EXIST, email)));
	}

	private void checkExistUser(String email) {
		
		log.debug("UserService.checkExistUser - Start - Request:  [{}]", email);
		
		Optional<User> user = userRepository.findByEmail(email);
		
		if(user.isPresent()) {
			
			throw new UserExistException(String.format(USER_WITH_EMAIL_EXIST, email));
			
		}
		
		log.debug("UserService.checkExistUser - Finish:  [{}]", email);
		
		
	}
	
	private void checkExistUser(Long idUser) {
		
		log.debug("UserService.checkExistUser - Start - idUser: [{}]", idUser);
		
		Optional<User> user = userRepository.findById(idUser);
		
		if(!user.isPresent()) {
			
			throw new UserNotExistException(String.format(USER_WITH_ID_NOT_EXIST, idUser));
			
		}
		
		log.debug("UserService.checkExistUser - Finish: idUser: [{}]", idUser);
		
		
	}
}
