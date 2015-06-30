import React from 'react/addons';
import Router from 'react-router';
import Reactable from 'reactable';
import { DefaultRoute, HashHistory, Link, Route, RouteHandler } from 'react-router';
import $ from 'jquery';
import Moment from 'moment';
import URL from 'url-parse';

var Table = Reactable.Table;
var path = getUrlPathPrefix('/batchSchedule.html') + "/batch/jobs";

var JobTable = React.createClass({
    handleSearchSubmit: function() {
        $.ajax({
            url: path,
            dataType: 'json',
            type: 'GET',
            success: function(searchResults) {
                this.setState({searchResults: searchResults});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(path, status, err.toString()+"; this should never happen!!!!");
            }.bind(this)
        });
    },
    componentDidMount: function() {
        this.handleSearchSubmit();
    },
    getInitialState: function () {
        return {searchResults: {'unscheduled': [], 'scheduled': []}};
    },
    render: function() {
        console.log("render parent");
        return (
            <div>
                <JobList searchResults={this.state.searchResults}/>
            </div>
        );
    }
});

function prettifyStepNames(stepNames) {
    if (!stepNames) {
        return (<span></span>)
    }
    return (
        <ul>
            {stepNames.map(function(ele) {
                return (<li>{ele}</li>)
            })}
        </ul>
    )
}

function prettifyDependencies(dependencies) {
    var rows = [];
    for (var dependency in dependencies) {
        if (dependencies.hasOwnProperty(dependency)) {
            rows.push(dependency + " (" + dependencies[dependency] + ")");
        }
    }
    return (
        <ul>
            {rows.map(function(row) {
                return (<li>{row}</li>)
            })}
        </ul>
    )
}

var JobList = React.createClass({
    render: function() {
        console.log("render JobList");
        var scheduled = this.props.searchResults.scheduled;
        var unscheduled = this.props.searchResults.unscheduled;
        var rows = scheduled.concat(unscheduled)
        rows = rows.map(function(row) {
            var formattedNextRunDate = (row.nextRunDate ? Moment(row.nextRunDate).format('M/DD/YYYY, h:mm:ss a') : '');
            row['formattedNextRunDate'] = formattedNextRunDate
            row['modifyUrl'] = <Link to="detail" params={{jobName: row.name, groupId: row.group}}>modify</Link>;
            row['steps'] = prettifyStepNames(row.stepNames)
            row['dependencyList'] = prettifyDependencies(row.dependencies)
            return row
        })
        return (
            <Table data={rows}
                   columns={[{key: 'modifyUrl', label: 'Actions'},{key: 'namespaceCode', label: 'Namespace'},{key: 'name', label: 'Name'}, {key: 'group', label: 'Group'},{key: 'formattedNextRunDate', label: 'Next Run Date'},{ key: 'steps', label: 'Steps'},{key: 'dependencyList',label: 'Dependencies'}]}
                   sortable={['namespaceCode','name','group','formattedNextRunDate']}
                   filterable={['namespaceCode','name','group']}/>
        );
    }
});

var JobDetail = React.createClass({
    componentDidMount: function() {
        var jobDetailEndpoint = getUrlPathPrefix('/batchSchedule.html') + "/batch/job/"+this.props.params.groupId+"/"+this.props.params.jobName;
        $.ajax({
            url: jobDetailEndpoint,
            dataType: 'json',
            type: 'GET',
            success: function(job) {
                this.setState({job: job});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(jobDetailEndpoint, status, err.toString());
            }.bind(this)
        })
    },
    getInitialState: function () {
        return {job: {}};
    },
    render: function() {
        return (
            <div>
                <ScheduledGroupMembershipToggle job={this.state.job}/>
                <UnscheduledJobForm job={this.state.job}/>
                <JobInfo job={this.state.job}/>
            </div>
        )
    }
});

var ScheduledGroupMembershipToggle = React.createClass({
    handleUnscheduleClick: function() {
        console.log("Unschedule!")
    },
    handleScheduleClick: function() {
        console.log("Schedule!")
    },
    render:function() {
        if (this.props.job.scheduled) {
            return (
                <button onClick={this.handleUnscheduleClick} value="Unschedule" type="button">Unschedule</button>
            )
        } else {
            return (
                <button onClick={this.handleScheduleClick} value="schedule" type="button">Schedule</button>
            )
        }
    }
});

var UnscheduledJobForm = React.createClass({
    getInitialState: function () {
        return {startStep: "", stopStep: "", startDateTime: "", resultsEMail: ""};
    },
    handleClick: function() {
        console.log("Handle Click!")
    },
    handleTextChange: function(name, event) {
        var stateUpdate = {}
        stateUpdate[name] = event.target.value
        this.setState(stateUpdate)
    },
    render:function() {
        if (this.props.job.group === 'unscheduled') {
            return (
                <div>
                    <p><label htmlFor="startStep">Start Step</label> <input type="text" value={this.state.startStep} size="2" onChange={this.handleTextChange.bind(this,"startStep")}/></p>
                    <p><label htmlFor="endStep">End Step</label> <input type="text" value={this.state.endStep} size="2" onChange={this.handleTextChange.bind(this,"endStep")}/></p>
                    <p><label htmlFor="startDateTime">Start Date/Time</label> <input type="text" value={this.state.startDateTime} onChange={this.handleTextChange.bind(this,"startDateTime")}/></p>
                    <p><label htmlFor="resultsEMail">Results E-Mail Address</label> <input type="text" value={this.state.resultsEMail} onChange={this.handleTextChange.bind(this,"resultsEMail")}/></p>
                    <button onClick={this.handleClick} value="schedule" type="button">New Schedule</button>
                </div>
            )
        } else {
            return (
                <span/>
            )
        }
    }
});

var JobInfo = React.createClass({
    render: function() {
        var formattedNextRunDate = (this.props.job.nextRunDate ? Moment(this.props.job.nextRunDate).format('M/DD/YYYY, h:mm:ss a') : '');
        return (
            <div>
                <ul>
                    <li>Namespace: {this.props.job.namespaceCode}</li>
                    <li>Name: {this.props.job.name}</li>
                    <li>Group: {this.props.job.group}</li>
                    <li>Status: {this.props.job.status}</li>
                    <li>Next Run Date: {formattedNextRunDate}</li>
                    <li>Steps: {prettifyStepNames(this.props.job.stepNames)}</li>
                    <li>Number of Steps: {this.props.job.numSteps}</li>
                    <li>Dependencies: {this.props.job.dependencyList}</li>
                </ul>
                <Link to="table">back</Link>
            </div>
        )
    }
})

function getUrlPathPrefix(page) {
    var path = URL(window.location.href).pathname;
    var index = path.indexOf(page);
    return path.substring(0, index);
}

var App = React.createClass({
    render () {
        return (
            <div>
                <RouteHandler/>
            </div>
        )
    }
});

let routes = (
    <Route handler={App}>
        <Route name="table" path="/" handler={JobTable}/>
        <Route name="detail" path="/job/:jobName/group/:groupId" handler={JobDetail}/>
    </Route>
);

Router.run(routes, function (Handler) {
    React.render(<Handler/>, document.getElementById('main'));
});
