import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { Tree, Form, Button, Input, message } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException, PageComponent } from '@/components/UIComponent';
import { newRole, addRole } from '../redux/roleService';

const FormItem = Form.Item;
const { TextArea } = Input;

export default function NewRole() {
    const [form] = Form.useForm();

    const navigate = useNavigate();

    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [uCheckedKeys, setCheckedKeys] = useState([]);
    const [uTreeData, setTreeData] = useState(undefined);

    const gotoList = () => {
        navigate('/role/list');
    };

    const onCheck = (checkedKeys) => {
        setCheckedKeys(checkedKeys);
    };

    const onFinish = (values) => {
        if (uCheckedKeys.length === 0) {
            message.error('请选择角色权限');
            return;
        }

        const body = {
            name: values.name,
            description: values.description,
            permIds: uCheckedKeys,
        };

        setConfirmLoading(true);

        addRole(body).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('新增角色成功。');
                gotoList();
            } else {
                setConfirmLoading(false);
                message.error(fetchStatus.message);
            }
        });
    };

    useEffect(() => {
        const body = {};

        newRole(body).then(({ fetchStatus, treeData }) => {
            setTreeData(treeData);
            setFetchStatus(fetchStatus);
        });
    }, []);

    const breadcrumbs = [
        <Link key='list' to='/role/list'>
            角色
        </Link>,
        '增加角色',
    ];

    if (uFetchStatus.loading) {
        return <PageLoading breadcrumbs={breadcrumbs} />;
    }
    if (!uFetchStatus.okey) {
        return <PageException breadcrumbs={breadcrumbs} fetchStatus={uFetchStatus} goBack={gotoList} />;
    }

    const treeDataArray = JSON.parse(uTreeData);

    return (
        <PageComponent breadcrumbs={breadcrumbs}>
            <Form onFinish={onFinish} form={form} labelAlign='left' labelCol={{ flex: '90px' }}>
                <FormItem
                    name='name'
                    label='角色名'
                    rules={[
                        {
                            required: true,
                            message: '请输入角色名',
                        },
                        {
                            min: 2,
                            message: '角色名最小长度是2',
                        },
                        {
                            max: 15,
                            message: '角色名最大长度是15',
                        },
                    ]}
                >
                    <Input autoComplete='off' maxLength='15' />
                </FormItem>
                <FormItem name='description' label='描述' labelCol={{ flex: '90px', style: { paddingLeft: '11px' } }}>
                    <TextArea rows={1} maxLength='255' />
                </FormItem>
                <div>角色权限：</div>
                <div className='mz_permission_tree'>
                    <Tree showLine checkable defaultExpandAll blockNode onCheck={onCheck} treeData={treeDataArray} />
                </div>
                <div className='mz-button-group'>
                    <Button type='primary' htmlType='submit' loading={confirmLoading}>
                        确定
                    </Button>
                    <Button onClick={gotoList}>取消</Button>
                </div>
            </Form>
        </PageComponent>
    );
}
