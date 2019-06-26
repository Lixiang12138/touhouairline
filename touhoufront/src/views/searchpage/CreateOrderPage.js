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

class CreateOrderPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    };
  }
  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, fieldsvalues) => {
      if (!err) {
        console.log('Received values of form: ', fieldsvalues);
        console.log('value', values);
        const props = this.props;
        const _this = this;
        const datas = {
          ...fieldsvalues,
        }
        axios.post('create_order', datas)
          .then(response => {
            const resultSet = response.data.result;
            if (resultSet.success) {
              console.log('准备跳转1')
              props.changePage(1, resultSet.object.depart, _this.state.returnTimeDisabled);
            }
          });
      }
    });
  };
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
    return (
      <Form {...formItemLayout} onSubmit={this.handleSubmit} labelAlign="left">
        <Form.Item label="乘机人姓名">
          {
            getFieldDecorator('name')(<Input placeholder="他" />)
          }
        </Form.Item>
        <Form.Item label="证件号">
          {
            getFieldDecorator('identify ', { initialValue: "31011119260817999X" })(<Input />)
          }
        </Form.Item>
        <Form.Item label="乘机人类型">
          {
            getFieldDecorator('type', { initialValue: "成人" })(<Input />)
          }
        </Form.Item>
        <Form.Item label="联系方式">
          {
            getFieldDecorator('phone', { initialValue: "13813881388" })(<Input />)
          }
        </Form.Item>
        <Button block type="primary" style={{ height: "40px" }} htmlType="submit">
          支付
        </Button>
      </Form>
    );
  }
}
const WrappedCreateOrderPage = Form.create({ name: 'create_order_form' })(CreateOrderPage);
export default WrappedCreateOrderPage;