/* eslint-disable */
import React from 'react';
import axios from 'axios';
import {
    Link
} from 'react-router-dom';
import {
    Button, Row, Col, Layout, Menu, Form, Icon, Input, DatePicker, Radio
} from 'antd';
import moment from 'moment'
const { Header, Footer, Sider, Content } = Layout;
import 'antd/dist/antd.css';

class FlightPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            pageIndex:0,//1:插入 2：更新
            returnTimeDisabled: true,
            Message:""
        };
    }
    handleSubmit = e => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsvalues) => {
            if (!err) {
                const dateFormat = 'YYYY/MM/DD';
                console.log('Received values of form: ', fieldsvalues);
                const values = {
                    ...fieldsvalues,
                    'departTime': fieldsvalues['departTime'].format(dateFormat),
                    'returnTime': fieldsvalues['returnTime'].format(dateFormat)
                }
                console.log('value', values);
                const props = this.props;
                const _this = this;
                const datas = {
                    departTime: values['departTime'],
                    arrivedTime: values['returnTime'],
                    departPlace: values['departPlace'],
                    destination: values['destination'],
                    flightNo:values['flightNo'],
                    planeNo:values['planeNo'],
                    economyPrice:values['economyPrice'],
                    premiumPrice:values['premiumPrice'],
                    firstPrice:values['firstPrice'],
                    boardingGate:values['boardingGate'],
                    mileage:values['mileage']
                }
                axios.post('add_flight', datas)
                    .then(response => {
                        const resultSet = response.data.result;
                        if(resultSet.success){
                            console.log("add success");
                            console.log(resultSet.success + resultSet.message);
                            _this.setState({Message:resultSet.message});
                        } else {
                            console.log(resultSet.Message);
                            _this.setState({Message:resultSet.message});
                        }
                    });
            }
        });
    };

    handleUpdate = e => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsvalues) => {
            if (!err) {
                const dateFormat = 'YYYY/MM/DD';
                console.log('Received values of form: ', fieldsvalues);
                const values = {
                    ...fieldsvalues,
                    'departTime': fieldsvalues['departTime'].format(dateFormat),
                    'returnTime': fieldsvalues['returnTime'].format(dateFormat)
                }
                console.log('value', values);
                const props = this.props;
                const _this = this;
                const datas = {
                    departTime: values['departTime'],
                    arrivedTime: values['returnTime'],
                    departPlace: values['departPlace'],
                    destination: values['destination'],
                    flightNo:values['flightNo'],
                    planeNo:values['planeNo'],
                    economyPrice:values['economyPrice'],
                    premiumPrice:values['premiumPrice'],
                    firstPrice:values['firstPrice'],
                    boardingGate:values['boardingGate'],
                    mileage:values['mileage']
                }
                axios.post('change_flight', datas)
                    .then(response => {
                        const resultSet = response.data.result;
                        if(resultSet.success){
                            console.log("change success");
                            console.log(resultSet.success + resultSet.message);
                            _this.setState({Message:resultSet.message});
                        } else {
                            console.log(resultSet.Message);
                            _this.setState({Message:resultSet.message});
                        }
                    });
            }
        });
    };

    handleDelete = e =>{
        console.log("delete");
        e.preventDefault();
        this.props.form.validateFields((err, fieldsvalues) => {
            if (!err) {
                console.log('value', values);
                const props = this.props;
                const _this = this;
                const datas = {
                    flightNo:values['flightNo'],
                }
                axios.post('delete_flight', datas)
                    .then(response => {
                        const resultSet = response.data.result;
                        if(resultSet.success){
                            console.log("delete success");
                            console.log(resultSet.success + resultSet.message);
                            _this.setState({Message:resultSet.message});
                        } else {
                            console.log(resultSet.Message);
                            _this.setState({Message:resultSet.message});
                        }
                    });
            }
        });
    }

    handleCancel = e =>{
        console.log("Cancel");
        const _this = this;
        _this.setState({pageIndex:0,Message:""});
    }

    SearchFlight = e =>{
        console.log("SearchFlight");
        const _this = this;
        _this.setState({pageIndex:4,Message:""});
    }

    GoToPageTwo = e =>{
        console.log("GoToPageTwo");
        const _this = this;
        _this.setState({pageIndex:2,Message:""});
    }
    GoToPageFour = e =>{
        console.log("GoToPageTwo");
        const _this = this;
        _this.setState({pageIndex:4,Message:""});
    }
    GoToPageZero = e =>{
        console.log("GoToPageTwo");
        const _this = this;
        _this.setState({pageIndex:0,Message:""});
    }



    render() {
        const { getFieldDecorator, getFieldsError, getFieldError, isFieldTouched } = this.props.form;
        const dateFormat = 'YYYY/MM/DD';
        const formItemLayout = {
            labelCol: {
                xs: 4,
                sm: 4
            },
            wrapperCol: {
                xs: 8,
                sm: 8
            }
        };
        const today = new moment(new Date(), dateFormat);
        const tomorrow = new moment(new Date().setDate(new Date().getDate() + 1));
        console.log(this.state.pageIndex);
        if(this.state.pageIndex==4){
            return (
                <Form {...formItemLayout} onSubmit={this.SearchFlight} labelAlign="left">
                    <Row>
                        <Col span={8} offset={4}>
                            查找航班:
                            <br/>
                            <Form.Item label="航班号">
                                {
                                    getFieldDecorator('flightNo', { initialValue: "1" })(<Input />)
                                }
                            </Form.Item>
                        </Col>
                        <Col span={2}>
                            <br/>
                            <Button block type="primary" style={{ height: "40px" }} onClick={this.GoToPageZero}>
                                查找
                            </Button>
                        </Col>
                    </Row>

                    <Row>
                        <br />
                        <Col span={8} offset={4}>
                            <div style={{color:"red"}}>{this.state.Message}</div>
                        </Col>
                    </Row>
                </Form>
            )
        }
        if(this.state.pageIndex==0){
            return (
                <Form {...formItemLayout} onSubmit={this.SearchFlight} labelAlign="left">
                    <Row>
                        <Col span={8} offset={4}>
                            查找航班:
                            <br/>
                            <Form.Item label="航班号">
                                {
                                    getFieldDecorator('flightNo', { initialValue: "1" })(<Input />)
                                }
                            </Form.Item>
                        </Col>
                        <Col span={2}>
                            <br/>
                            <Button block type="primary" style={{ height: "40px" }} onClick={this.GoToPageZero}>
                                查找
                            </Button>
                        </Col>
                    </Row>
                    <Row>
                        <br />
                        <Col span={3} offset={4}>
                            <Button block type="primary" style={{ height: "40px" }} onClick={this.GoToPageTwo}>
                                修改
                            </Button>
                        </Col>
                        <Col span={3} offset={2}>
                            <Button block type="primary" style={{ height: "40px" }} onClick={this.GoToPageFour}>
                                删除
                            </Button>
                        </Col>
                    </Row>
                    <Row>
                        <br />
                        <Col span={8} offset={4}>
                            <div style={{color:"red"}}>{this.state.Message}</div>
                        </Col>
                    </Row>
                </Form>
            );
        }else {
        return (
            <Form {...formItemLayout} onSubmit={this.state.index==1?this.handleSubmit:this.handleUpdate} labelAlign="left">
                <Row>
                    <Col span={8} offset={4}>
                        <Form.Item label="航班号">
                            {
                                getFieldDecorator('flightNo', { initialValue: "1" })(<Input disabled={this.state.index!=1}/>)
                            }
                        </Form.Item>
                    </Col>
                    <Col span={8}>
                        <Form.Item label="飞机号">
                            {
                                getFieldDecorator('planeNo', { initialValue: "1" })(<Input />)
                            }
                        </Form.Item>
                    </Col>
                </Row>
                <Row>
                    <Col span={8} offset={4}>
                        <Form.Item label="出发城市">
                            {
                                getFieldDecorator('departPlace', { initialValue: "上海" })(<Input />)
                            }
                        </Form.Item>
                    </Col>
                    <Col span={8}>
                        <Form.Item label="到达城市">
                            {
                                getFieldDecorator('destination', { initialValue: "北京" })(<Input />)
                            }
                        </Form.Item>
                    </Col>
                </Row>
                <Row>
                    <Col span={8} offset={4}>
                        <Form.Item label="登机口">
                            {
                                getFieldDecorator('boardingGate', { initialValue: "123" })(<Input />)
                            }
                        </Form.Item>
                    </Col>
                    <Col span={8}>
                        <Form.Item label="飞行里程">
                            {
                                getFieldDecorator('mileage', { initialValue: "65536" })(<Input />)
                            }
                        </Form.Item>
                    </Col>
                </Row>
                <Row>
                    <Col span={8} offset={4}>
                        <Form.Item label="出发日期">
                            {
                                getFieldDecorator('departTime', { initialValue: today })(<DatePicker format={dateFormat} />)
                            }
                        </Form.Item>
                    </Col>
                    <Col span={8}>
                        <Form.Item label="返程日期">
                            {
                                getFieldDecorator('returnTime', { initialValue: tomorrow })(<DatePicker format={dateFormat} />)
                            }
                        </Form.Item>
                    </Col>
                </Row>
                <Row>
                    <Col span={8} offset={4}>
                        <Form.Item label="经济舱价格">
                            {
                                getFieldDecorator('economyPrice', { initialValue: "123" })(<Input />)
                            }
                        </Form.Item>
                    </Col>
                    <Col span={8}>
                        <Form.Item label="公务舱价格">
                            {
                                getFieldDecorator('premiumPrice', { initialValue: "456" })(<Input />)
                            }
                        </Form.Item>
                    </Col>
                </Row>
                <Row>
                    <br />
                    <Col span={8} offset={4}>
                        <Form.Item label="头等舱价格">
                            {
                                getFieldDecorator('firstPrice', { initialValue: "789" })(<Input />)
                            }
                        </Form.Item>
                    </Col>
                    <Col span={2} offset={1}>
                        <Button block type="primary" style={{ height: "40px" }} htmlType="submit">
                            {this.state.index==1?"插入":"更新"}
                        </Button>
                    </Col>
                    <Col span={2} offset={1}>
                        <Button block type="primary" style={{ height: "40px" }} onClick={this.handleCancel}>
                            返回
                        </Button>
                    </Col>
                </Row>
                <Row>
                    <br />
                    <Col span={8} offset={4}>
                        <div style={{color:"red"}}>{this.state.Message}</div>
                    </Col>
                </Row>
            </Form>
        );}
    }
}
const WrappedFlightPage = Form.create({ name: 'add_flight_form' })(FlightPage);
export default WrappedFlightPage;