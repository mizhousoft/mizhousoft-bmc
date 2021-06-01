import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Steps } from 'antd';
import InputAccountInfo from './InputAccountInfo';
import SelectRoleForm from './SelectRoleForm';
import ConfirmAccountInfoForm from './ConfirmAccountInfoForm';

const { Step } = Steps;

class NewAccount extends Component {
    constructor(props) {
        super(props);
        this.state = {
            current: 0,

            formData: {
                name: '',
                status: 2,
                phoneNumber: '',
                password: '',
                confirmPassword: '',
                selectedRoles: [],
            },
        };
    }

    prevStep = () => {
        this.setState((prevState) => ({ current: prevState.current - 1 }));
    };

    nextStep = (newFormData) => {
        this.setState((prevState) => ({
            current: prevState.current + 1,
            formData: newFormData,
        }));
    };

    gotoList = () => {
        const { history } = this.props;
        history.push('/account/list');
    };

    renderBody = () => (
        <>
            <Steps current={this.state.current} size='small' style={{ padding: '15px 10px 25px 10px' }}>
                <Step title='输入帐号' />
                <Step title='选择角色' />
                <Step title='确认帐号' />
            </Steps>
            <div style={{ padding: '0px 25px' }}>
                {this.state.current === 0 && (
                    <InputAccountInfo
                        formData={this.state.formData}
                        nextStep={this.nextStep}
                        gotoList={this.gotoList}
                    />
                )}
                {this.state.current === 1 && (
                    <SelectRoleForm
                        formData={this.state.formData}
                        prevStep={this.prevStep}
                        nextStep={this.nextStep}
                        gotoList={this.gotoList}
                    />
                )}
                {this.state.current === 2 && (
                    <ConfirmAccountInfoForm
                        formData={this.state.formData}
                        prevStep={this.prevStep}
                        gotoList={this.gotoList}
                    />
                )}
            </div>
        </>
    );

    render() {
        return (
            <>
                <div className='mz-page-head'>
                    <div className='title'>帐号 / 增加帐号</div>
                </div>

                <div className='mz-page-content'>
                    <div className='mz-page-content-body'>{this.renderBody()}</div>
                </div>
            </>
        );
    }
}

export default withRouter(NewAccount);
