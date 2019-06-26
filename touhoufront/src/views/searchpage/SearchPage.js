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


class SearchPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      returnTimeDisabled: true
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
          departure: {
            departTime: values['departTime'],
            returnTime: values['returnTime'],
            departPlace: values['departPlace'],
            arrivedPlace: values['destination'],
            isOneWay: _this.state.returnTimeDisabled
          }
        }
        axios.post('query_one_way_ticket', datas)
          .then(response => {
            const data = response.data.result;
          });
      }
    });
  };
  render() {
    const { getFieldDecorator, getFieldsError, getFieldError, isFieldTouched } = this.props.form;
    const dateFormat = 'YYYY/MM/DD';
    const formItemLayout = {
      labelCol: {
        xs: 8,
        sm: 8
      },
      wrapperCol: {
        xs: 14,
        sm: 10
      }
    };
    const today = new moment(new Date(), dateFormat);
    const tomorrow = new moment(new Date().setDate(new Date().getDate() + 1));
    return (
      <Form {...formItemLayout} onSubmit={this.handleSubmit} labelAlign="left">
        <Row>
          <Col span={6} offset={4}>
            <Radio.Group defaultValue="one-way" buttonStyle="solid" style={{ height: "64px" }}>
              <Radio.Button onClick={() => {
                this.setState({ returnTimeDisabled: true });
              }} value="one-way">
                单程
                  </Radio.Button>
              <Radio.Button onClick={() => this.setState({ returnTimeDisabled: false })} value="two-way">
                往返
                  </Radio.Button>
            </Radio.Group>

          </Col>
        </Row>
        <Row>
          <Col span={6} offset={4}>
            <Form.Item label="出发城市">
              {
                getFieldDecorator('departPlace', { initialValue: "上海" })(<Input />)
              }
            </Form.Item>
          </Col>
          <Col span={6}>
            <Form.Item label="到达城市">
              {
                getFieldDecorator('destination', { initialValue: "北京" })(<Input />)
              }
            </Form.Item>
          </Col>
        </Row>
        <Row>
          <Col span={6} offset={4}>
            <Form.Item label="出发日期">
              {
                getFieldDecorator('departTime', { initialValue: today })(<DatePicker format={dateFormat} />)
              }
            </Form.Item>
          </Col>
          <Col span={6}>
            <Form.Item label="返程日期">
              {
                getFieldDecorator('returnTime', { initialValue: tomorrow })(<DatePicker disabled={this.state.returnTimeDisabled} />)
              }
            </Form.Item>
          </Col>
        </Row>
        <Row>
          <br />
          <Col span={3} offset={4}>
            <Button block type="primary" style={{ height: "40px" }} htmlType="submit">
              搜索
                </Button>
          </Col>
        </Row>
      </Form>
    );
  }
}
const WrappedSearchPage = Form.create({ name: 'search_flight_form' })(SearchPage);
export default WrappedSearchPage;