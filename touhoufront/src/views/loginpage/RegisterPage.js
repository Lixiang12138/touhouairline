/* eslint-disable */
import React from 'react';
import axios from 'axios';
import {
	Input, Button
} from 'antd';
import {
	PrimaryButton, Label,TextField
} from 'office-ui-fabric-react';

import '../../App.css';

class RegisterPage extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			userName: "",
			password: "",
			passwordAgain: "",
			email: "",
			phone: "",
			nickName: "",
			showMessage: false,
			errorMessage: ""
		}
	}

	handleInputChange = (name, value) => {
		this.setState({
			[name]: value
		});
	}

	handleClick = () => {
		const userEntity = {
			user:
			{
				userName: this.state.userName,
				password: this.state.password,
				email: this.state.email,
				userPhone: this.state.phone,
				nickName: this.state.nickName
			}
			
		};
		const props = this.props;
		const _this = this;

		axios.post('register', userEntity)
			.then(function (response) {
				const data = response.data.result;
				if (data.success == true) {
					props.success();
					alert("注册成功！请前往登录");
					_this.setState({
						showMessage: false,
						errorMessage: ""
					});
				} else {
					_this.setState({
						showMessage: true,
						errorMessage: data.message
					});
				}
			})
	}

	render() {
		const errorMessage = this.state.showMessage
			? (
				<Label style={{ color:"#FF0000" }}>{this.state.errorMessage}</Label>
			) : undefined;

		return (
			<div style={{ textAlign: "center", marginLeft: "30%", marginRight: "30%" }}>
				<TextField label="请输入用户名" onChange={e => this.handleInputChange('userName', e.target.value)} />
				<TextField label="密码" type="password" onChange={e => this.handleInputChange('password', e.target.value)} />
				<TextField label="再次输入密码" type="password" onChange={e => this.handleInputChange('passwordAgain', e.target.value)} />
				<TextField label="昵称" onChange={e => this.handleInputChange('nickName', e.target.value)} />
				<TextField label="电话" onChange={e => this.handleInputChange('phone', e.target.value)} />
				<TextField label="邮箱" onChange={e => this.handleInputChange('email', e.target.value)} />
				<PrimaryButton style={{ marginTop: "16px" }} onClick={this.handleClick}>注册</PrimaryButton>
				{errorMessage}
			</div>
		);
	}
}

export default RegisterPage;
