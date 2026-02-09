import { useEffect, useState } from 'react';
import { Button, Form, Tree } from 'antd';
import { useNavigate, useParams } from 'react-router';

import PageComponent from '@/components/PageComponent';
import PageException from '@/components/PageException';
import PageLoading from '@/components/PageLoading';
import { LOADING_FETCH_STATUS } from '@/config/common';
import httpRequest from '@/utils/http-request';

const FormItem = Form.Item;

export default function RoleInfo() {
    const navigate = useNavigate();
    const { id } = useParams();

    const [fetchStatus, setFetchStatus] = useState(LOADING_FETCH_STATUS);
    const [role, setRole] = useState(undefined);
    const [treeData, setTreeData] = useState(undefined);

    const gotoList = () => {
        navigate('/role/list');
    };

    useEffect(() => {
        const requestBody = {
            url: '/role/fetchRoleInfo.action',
            data: {
                id,
            },
        };

        httpRequest.get(requestBody).then(({ fetchStatus, role, treeData }) => {
            setRole(role);
            setTreeData(treeData);
            setFetchStatus(fetchStatus);
        });
    }, []);

    const breadcrumbs = [{ title: '角色', path: '/role/list' }, { title: '查看角色' }];

    if (fetchStatus.loading) {
        return <PageLoading breadcrumbs={breadcrumbs} />;
    }
    if (!fetchStatus.okey) {
        return <PageException breadcrumbs={breadcrumbs} fetchStatus={fetchStatus} goBack={gotoList} />;
    }

    const treeDataList = JSON.parse(treeData);

    return (
        <PageComponent breadcrumbs={breadcrumbs}>
            <Form labelAlign='left' labelCol={{ flex: '80px' }}>
                <FormItem label='角色名'>{role.displayNameCN}</FormItem>
                <FormItem label='描述'>{role.descriptionCN}</FormItem>
                <div>角色权限：</div>
                <div className='mz_permission_tree'>
                    <Tree showLine defaultExpandAll blockNode treeData={treeDataList} />
                </div>
                <div>
                    <Button type='primary' onClick={gotoList}>
                        返回
                    </Button>
                </div>
            </Form>
        </PageComponent>
    );
}
