import { useEffect, useState } from 'react';
import { Button, Form, Input, message, Tree } from 'antd';
import { useNavigate } from 'react-router';

import PageComponent from '@/components/PageComponent';
import PageException from '@/components/PageException';
import PageLoading from '@/components/PageLoading';
import { LOADING_FETCH_STATUS } from '@/config/common';
import httpRequest from '@/utils/http-request';

const FormItem = Form.Item;
const { TextArea } = Input;

export default function NewRole() {
    const [form] = Form.useForm();

    const navigate = useNavigate();

    const [fetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [checkedKeys, setCheckedKeys] = useState([]);
    const [treeData, setTreeData] = useState(undefined);

    const gotoList = () => {
        navigate('/role/list');
    };

    const onCheck = (checkedKeys) => {
        setCheckedKeys(checkedKeys);
    };

    const onFinish = (values) => {
        if (checkedKeys.length === 0) {
            message.error('请选择角色权限');
            return;
        }

        const requestBody = {
            url: '/role/addRole.action',
            data: {
                name: values.name?.trim(),
                description: values.description?.trim(),
                permIds: checkedKeys,
            },
        };

        setConfirmLoading(true);

        httpRequest.post(requestBody).then(({ fetchStatus }) => {
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
        const requestBody = {
            url: '/role/newRole.action',
            data: {},
        };

        httpRequest.get(requestBody).then(({ fetchStatus, treeData }) => {
            setTreeData(treeData);
            setFetchStatus(fetchStatus);
        });
    }, []);

    const breadcrumbs = [{ title: '角色', path: '/role/list' }, { title: '增加角色' }];

    if (fetchStatus.loading) {
        return <PageLoading breadcrumbs={breadcrumbs} />;
    }
    if (!fetchStatus.okey) {
        return <PageException breadcrumbs={breadcrumbs} fetchStatus={fetchStatus} goBack={gotoList} />;
    }

    const treeDataArray = JSON.parse(treeData);

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
