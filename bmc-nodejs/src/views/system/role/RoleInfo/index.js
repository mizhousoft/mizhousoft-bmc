import React, { Component } from 'react';
import { withRouter, Link } from 'react-router-dom';
import { Tree, Form, Button } from 'antd';
import { LOADING_FETCH_STATUS } from '@/constants/common';
import { PageLoading, PageException } from '@/components/UIComponent';
import FormFlex from '@/constants/flex';
import { fetchRoleInfo } from '../redux/roleService';

const FormItem = Form.Item;
const { TreeNode } = Tree;

class RoleInfo extends Component {
    constructor(props) {
        super(props);
        this.state = {
            fetchStatus: LOADING_FETCH_STATUS,
            id: props.match.params.id,

            role: undefined,
            treeData: undefined,
        };
    }

    gotoList = () => {
        const { history } = this.props;
        history.push('/role/list');
    };

    renderTreeNodes = (data) =>
        data.map((item) => {
            if (item.children) {
                return (
                    <TreeNode title={item.title} key={item.key} dataRef={item}>
                        {this.renderTreeNodes(item.children)}
                    </TreeNode>
                );
            }
            return <TreeNode {...item} key={item.key} />;
        });

    componentDidMount() {
        const body = {
            id: this.state.id,
        };

        fetchRoleInfo(body).then(({ fetchStatus, role, treeData }) => {
            this.setState({
                fetchStatus,
                role,
                treeData,
            });
        });
    }

    renderBody = () => {
        const { fetchStatus, role, treeData } = this.state;

        if (fetchStatus.loading) {
            return <PageLoading />;
        }
        if (!fetchStatus.okey) {
            return <PageException fetchStatus={fetchStatus} goBack={this.gotoList} />;
        }

        const treeDataArray = JSON.parse(treeData);

        return (
            <Form labelAlign='left'>
                <FormItem {...FormFlex.w100_lg4_required} label='用户名'>
                    {role.displayNameCN}
                </FormItem>
                <FormItem {...FormFlex.w100_lg4_required} label='描述'>
                    {role.descriptionCN}
                </FormItem>
                <div>角色权限：</div>
                <div className='mz_permission_tree'>
                    <Tree showLine defaultExpandAll>
                        {this.renderTreeNodes(treeDataArray)}
                    </Tree>
                </div>
                <div>
                    <Button type='primary' onClick={this.gotoList}>
                        返回
                    </Button>
                </div>
            </Form>
        );
    };

    render() {
        return (
            <>
                <div className='mz-page-head'>
                    <div className='title'>
                        <Link to='/role/list'>角色</Link> / 查看角色
                    </div>
                </div>

                <div className='mz-page-content'>
                    <div className='mz-page-content-body'>{this.renderBody()}</div>
                </div>
            </>
        );
    }
}

export default withRouter(RoleInfo);
