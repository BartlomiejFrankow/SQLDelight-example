package com.example.sqldelightexample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sqldelightexample.R
import db.person.PersonEntity

@Composable
fun PersonListScreen(
    viewModel: PersonListViewModel = hiltViewModel()
) {

    val persons = viewModel.persons
        .collectAsState(initial = emptyList())
        .value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        MainView(persons, viewModel)
        DialogView(viewModel)
    }

}

@Composable
private fun MainView(persons: List<PersonEntity>, viewModel: PersonListViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PersonsListView(
            persons, viewModel,
            Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        // Below list
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = viewModel.firstNameText,
                onValueChange = viewModel::onFirstNameChange,
                placeholder = {
                    Text(text = stringResource(R.string.first_name))
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = viewModel.lastNameText,
                onValueChange = viewModel::onLastNameChange,
                placeholder = {
                    Text(text = stringResource(R.string.last_name))
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = viewModel::onInsertPersonClick) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = stringResource(R.string.insert_person),
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
private fun PersonsListView(persons: List<PersonEntity>, viewModel: PersonListViewModel, modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        items(persons) { person ->
            PersonItem(
                person = person,
                onItemClick = {
                    viewModel.getPersonById(person.id)
                },
                onDeleteClick = {
                    viewModel.onDeletePersonClick(person.id)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun DialogView(viewModel: PersonListViewModel) {
    viewModel.personDetails?.let { details ->
        Dialog(onDismissRequest = viewModel::onPersonDetailsDialogDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "${details.firstName} ${details.lastName}")
            }
        }
    }
}

@Composable
fun PersonItem(
    modifier: Modifier = Modifier,
    person: PersonEntity,
    onItemClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clickable { onItemClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = person.firstName,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = stringResource(R.string.delete_person),
                tint = Color.Gray
            )
        }
    }

}
