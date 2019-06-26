/* eslint-disable */
import React from 'react';
import axios from 'axios';
import {
    Link
} from 'react-router-dom';
import {
    Button, Row, Col, Layout, Menu, Form, Icon, Input, DatePicker, Radio, Collapse, Descriptions, List, Card, Modal
} from 'antd';
const { Header, Footer, Sider, Content } = Layout;
import 'antd/dist/antd.css';
import CreateOrderPage from './CreateOrderPage';

const { Panel } = Collapse;

class BookingPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
        };
    }
    handleOk = () => {
        this.setState();
    };

    handleCancel = () => {
        this.setState({ visible: false });
    };
    /*transferToCreateOrder = (element) => {
        axios.post('show_detail_by_flight', element)
            .then(response => {
                const resultSet = response.data.result;
                if (resultSet.success) {
                    this.props.changePage(2,element,this.props.isSingle);
                }
            });
    }*/
    showFlightList = (flightArray) => {
        const ret = [];
        flightArray.forEach(element => {
            ret.push(
                <Descriptions bordered>
                    <Descriptions.Item label="出发机场">
                        {element.departPlace}
                    </Descriptions.Item>
                    <Descriptions.Item label="到达机场">
                        {element.destination}
                    </Descriptions.Item>
                    <Descriptions.Item label="航班号">
                        {element.flightNo}
                    </Descriptions.Item>
                    <Descriptions.Item label="出发时间">
                        {element.departTime}
                    </Descriptions.Item>
                    <Descriptions.Item label="到达时间(当地)">
                        {element.arrivedTime}
                    </Descriptions.Item>
                    <Descriptions.Item label="飞机编号">
                        {element.planeByPlaneNo.planeNo}
                    </Descriptions.Item>
                    <Descriptions.Item label="飞机型号">
                        {element.planeByPlaneNo.planeType}
                    </Descriptions.Item>
                    <Descriptions.Item>
                        <Button onClick={() => {
                            console.log('准备跳转下单');
                            this.props.changePage(2, null, this.props.isSingle);
                        }}>订票</Button>
                    </Descriptions.Item>
                </Descriptions>
            )
        });
        return ret;
    }
    render() {
        const departFlightsTags = (
            <Panel header="请选择去程航班" key="1">
                <CreateOrderPage />
            </Panel>
        );
        const returnFlightsTags = (
            <Panel header="请选择回程航班" key="2">
                <CreateOrderPage />
            </Panel>
        );
        return (
            <Layout>
                <Header>
                    请选择去程航班
                </Header>
                <Content>
                    {this.showFlightList(this.props.object)}
                </Content>
            </Layout>
        );
    }
}
export default BookingPage;