import React, { useState } from 'react';
import { Steps } from 'antd';
import { useNavigate } from 'react-router-dom';

import ConfirmAccountInfoForm from './ConfirmAccountInfoForm';
import InputAccountInfo from './InputAccountInfo';
import SelectRoleForm from './SelectRoleForm';
import { PageComponent } from '@/components/UIComponent';

const { Step } = Steps;

export default function NewAccount() {
    const navigate = useNavigate();

    const [current, setCurrent] = useState(0);
    const [formData, setFormData] = useState({
        name: '',
        status: 2,
        phoneNumber: '',
        password: '',
        confirmPassword: '',
        selectedRoles: [],
    });

    const prevStep = () => {
        const value = current - 1;
        setCurrent(value);
    };

    const nextStep = (newFormData) => {
        const value = current + 1;
        setCurrent(value);
        setFormData(newFormData);
    };

    const gotoList = () => {
        navigate('/account/list');
    };

    return (
        <PageComponent breadcrumbs={['帐号', '增加帐号']}>
            <Steps current={current} size='small' style={{ padding: '15px 10px 25px 10px' }}>
                <Step title='输入帐号' />
                <Step title='选择角色' />
                <Step title='确认帐号' />
            </Steps>
            <div style={{ padding: '0px 25px' }}>
                {current === 0 && <InputAccountInfo formData={formData} nextStep={nextStep} gotoList={gotoList} />}
                {current === 1 && (
                    <SelectRoleForm formData={formData} prevStep={prevStep} nextStep={nextStep} gotoList={gotoList} />
                )}
                {current === 2 && (
                    <ConfirmAccountInfoForm formData={formData} prevStep={prevStep} gotoList={gotoList} />
                )}
            </div>
        </PageComponent>
    );
}
