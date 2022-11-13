import React, { useState, useEffect } from 'react';
import { useNavigate, Link, useParams } from 'react-router-dom';
import { Tree, Form, Button, Input, message } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException, PageComponent } from '@/components/UIComponent';
import { editRole, modifyRole } from '../redux/roleService';

const FormItem = Form.Item;
const { TreeNode } = Tree;
const { TextArea } = Input;

export default function EditRole() {
    const [form] = Form.useForm();

    const navigate = useNavigate();
    const { id } = useParams();

    const [uFetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [uRole, setRole] = useState(undefined);
    const [uCheckedKeys, setCheckedKeys] = useState([]);
    const [uTreeData, setTreeData] = useState(undefined);

    const gotoList = () => {
        navigate('/role/list');
    };

    const onCheck = (checkedKeys) => {
        setCheckedKeys(checkedKeys);
    };

    const renderTreeNodes = (data) =>
        data.map((item) => {
            if (item.children) {
                return (
                    <TreeNode title={item.title} key={item.key} dataRef={item}>
                        {renderTreeNodes(item.children)}
                    </TreeNode>
                );
            }
            return <TreeNode {...item} key={item.key} />;
        });

    const onFinish = (values) => {
        if (uCheckedKeys.length === 0) {
            message.error('请选择角色权限');
            return;
        }

        const body = {
            id,
            name: values.name,
            description: values.description,
            permIds: uCheckedKeys,
        };

        setConfirmLoading(true);

        modifyRole(body).then(({ fetchStatus }) => {
            if (fetchStatus.okey) {
                message.success('修改角色成功。');
                gotoList();
            } else {
                setConfirmLoading(false);
                message.error(fetchStatus.message);
            }
        });
    };

    useEffect(() => {
        const body = {
            id,
        };

        editRole(body).then(({ fetchStatus, role, treeData, checkedKeys = [] }) => {
            setRole(role);
            setTreeData(treeData);
            setCheckedKeys(checkedKeys);
            setFetchStatus(fetchStatus);
        });
    }, []);

    const pageTitle = (
        <>
            <Link to='/role/list'>角色</Link> / 编辑角色
        </>
    );

    if (uFetchStatus.loading) {
        return <PageLoading title={pageTitle} />;
    }
    if (!uFetchStatus.okey) {
        return <PageException title={pageTitle} fetchStatus={uFetchStatus} goBack={gotoList} />;
    }

    const treeDataArray = JSON.parse(uTreeData);

    return (
        <PageComponent title={pageTitle}>
            <Form
                onFinish={onFinish}
                form={form}
                labelAlign='left'
                labelCol={{ flex: '90px' }}
                initialValues={{
                    name: uRole.displayNameCN,
                    description: uRole.descriptionCN,
                }}
            >
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
                    <Tree showLine checkable defaultExpandAll defaultCheckedKeys={uCheckedKeys} onCheck={onCheck}>
                        {renderTreeNodes(treeDataArray)}
                    </Tree>
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
